package com.example.demo.netty.Mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.util.Arrays;
import java.util.Objects;

/**
 * 解码
 *
 * @author yuan dian
 * @date 2019/1/22
 * @time 9:02
 */
@Slf4j
public class LnContextDecoder extends ProtocolDecoderAdapter {
    private final AttributeKey lnBufferKey = new AttributeKey(getClass(), "lnBuffer");

    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) {
        // 标记是否使用的缓冲区
        boolean useSessionBuffer = false;
        IoBuffer cachedBuffer = (IoBuffer) ioSession.getAttribute(lnBufferKey);
        if (Objects.isNull(cachedBuffer)) {
            cachedBuffer = ioBuffer;
        } else {
            // 将新报文放入缓冲区
            cachedBuffer.put(ioBuffer);
            cachedBuffer.flip();
            useSessionBuffer = true;
        }

        // 解析报文，并转换自定义协议对象
        doDecode(cachedBuffer, protocolDecoderOutput);

        // 报文解析完成后，还有剩余数据，说明出现了 半包
        if (cachedBuffer.hasRemaining()) {
            if (useSessionBuffer) {
                // 将未读取数据转移到缓冲区的最前面
                cachedBuffer.compact();
            } else {
                storeRemainingInSession(cachedBuffer, ioSession);
            }
        } else if (useSessionBuffer) {
            ioSession.removeAttribute(lnBufferKey);
        }
    }

    /**
     * 解析报文
     */
    private void doDecode(IoBuffer ioBuffer, ProtocolDecoderOutput out) {
        // 循环获取报文（一帧报文的全长大于8）
        while (ioBuffer.remaining() > 8) {
            // 使用标记，以便在出现“半包”时恢复position
            ioBuffer.mark();

            // 判断是否为协议的起始位置
            byte b = ioBuffer.get();
            if (ContextProtocol.HEADER == b) {
                // 获取数据长度，无符号数，所以要转成int
                int length = Byte.toUnsignedInt(ioBuffer.get());

                // 判断缓冲区数据是否覆盖了剩余报文
                if (ioBuffer.remaining() >= length + 2) {
                    byte[] array = new byte[length + 2];
                    ioBuffer.get(array);
                    try {
                        ContextProtocol protocol = ContextProtocol.convertUpContextProtocol(array);
                        out.write(protocol);
                    } catch (Exception e) {
                        log.error("解析报文实体类时异常，bytes:{}", Arrays.toString(array), e);
                    }
                } else {
                    // 半包场景，此时需要恢复 position，等待下次解析
                    ioBuffer.reset();
                    break;
                }
            }
        }
    }

    /**
     * 设置缓冲区
     */
    private void storeRemainingInSession(IoBuffer ioBuffer, IoSession ioSession) {
        // 初始化一个业务数据缓冲区
        IoBuffer cachedBuffer = IoBuffer.allocate(ioBuffer.capacity()).setAutoExpand(true);
        // 大小端跟 ioBuffer保持一致
        cachedBuffer.order(ioBuffer.order());
        // 将未解析完的报文放入新的缓冲区
        cachedBuffer.put(ioBuffer);
        // 将缓冲区设置到session中
        ioSession.setAttribute(lnBufferKey, cachedBuffer);
    }
}