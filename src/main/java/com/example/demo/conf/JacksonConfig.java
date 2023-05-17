package com.example.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class JacksonConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        ObjectMapper objectMapper = converter.getObjectMapper();
//        // 时间格式化
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
//        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//        SimpleModule simpleModule = new SimpleModule();
//        // Long类型序列化成字符串，避免Long精度丢失
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//
//        objectMapper.registerModule(simpleModule).registerModule(new ParameterNamesModule());
//
//        // 设置格式化内容
//        converter.setObjectMapper(objectMapper);
//        converters.add(0, converter);
    }
}
