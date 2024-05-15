package com.example.demo.po;


import cn.hutool.extra.spring.SpringUtil;
import com.example.demo.annotation.SerizlizerDict;
import com.example.demo.service.CustomerImplTest;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class DictSerializer extends JsonSerializer<Integer> implements ContextualSerializer {


    private String code;
    @Override
    public void serialize(Integer integer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println(":dfd");
        jsonGenerator.writeString(String.valueOf(integer));
        CustomerImplTest bean = SpringUtil.getBean(CustomerImplTest.class);
        System.out.println("sdf");
    }


    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), Integer.class)) {
                SerizlizerDict serizlizerDict = beanProperty.getAnnotation(SerizlizerDict.class);
                if (serizlizerDict == null) {
                    serizlizerDict = beanProperty.getContextAnnotation(SerizlizerDict.class);
                }
                if (serizlizerDict != null) {
                    return new DictSerializer(serizlizerDict.value());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }


}
