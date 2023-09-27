package com.example.demo.delay;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public abstract class DelayBlockingQueueAwaitSignal implements InitializingBean {

    private final ExecutorService executorService = ThreadUtil.newSingleExecutor();
    private final transient ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    public static final String dataId = "com.comma.teeth.DelayBlockingQueueAwaitSignal";
    public static final String groupId = "teeth";

    @Autowired
    protected StringRedisTemplate redisTemplate;
    @Autowired
    private ConfigService configService;


    public abstract void addRedisZSet(Long id, Date dateTime);

    protected abstract void updateState();


    @SneakyThrows
    public void notifyListenerConfigService() {
        try {
            //修改配置文件时监听
            configService.addListener(dataId, groupId, new AbstractListener() {
                @SneakyThrows
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("DelayBlockingQueueAwaitSignal notifyListener start....");
                    executorService.execute(() -> updateState());
                }
            });
        } catch (NacosException e) {
            log.error("DelayBlockingQueueAwaitSignal configService.addListener error", e);
        }

    }


    @SneakyThrows
    protected Long take() {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (; ; ) {
                Iterator<ZSetOperations.TypedTuple<String>> iterator = getOperations();
                if (!iterator.hasNext()){
                    notFull.await();
                }else{
                    ZSetOperations.TypedTuple<String> typedTuple = iterator.next();
                    long delay = TimeUnit.MILLISECONDS.convert(typedTuple.getScore().longValue() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    if(delay <= 0){
                        Long remove = redisTemplate.opsForZSet().remove(typedTuple.getValue());
                        if(remove > 0){
                            return Long.valueOf(typedTuple.getValue());
                        }
                    }else{
                        notFull.awaitNanos(delay);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.execute(() -> updateState());
        notifyListenerConfigService();
    }


    /**
     * 新增和修改数据 触发 唤醒消费者
     */
    protected void notifyDelayBlockingQueue() {
        Iterator<ZSetOperations.TypedTuple<String>> iterator = getOperations();
        if(!iterator.hasNext()){
            try {
                this.lock.lockInterruptibly();
                log.info("notifyDelayBlockingQueue this:{}",this.getClass().getSimpleName());
                this.notFull.signal();
            } catch (Throwable e) {
                log.error("notifyDelayBlockingQueue error", e);
            } finally {
                this.lock.unlock();
            }
        }
    }

    private Iterator<ZSetOperations.TypedTuple<String>> getOperations(){
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisTemplate.opsForZSet().rangeWithScores(this.getClass().getSimpleName(), 0, 0);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = typedTuples.iterator();
        return iterator;
    }


}

