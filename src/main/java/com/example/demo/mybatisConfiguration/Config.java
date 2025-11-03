package com.example.demo.mybatisConfiguration;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public MybatisLogPlusInterceptor mybatisLogPlusInterceptor() {
        MybatisLogPlusInterceptor interceptor = new MybatisLogPlusInterceptor();
        return interceptor;
    }

//    @Bean
//    public MybatisSqlInterceptor mybatisSqlPlusInterceptor() {
//        MybatisSqlInterceptor interceptor = new MybatisSqlInterceptor();
//        return interceptor;
//    }
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
