package com.example.demo.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class Date2LongSerializer extends JsonSerializer<EnumsObject> {
    @Override
    public void serialize(EnumsObject o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        System.out.println("asdf");
    }
}
