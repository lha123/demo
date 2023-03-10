package com.example.demo.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
public class EnumsObject {
    private String code;
    private String value;

    public EnumsObject(String code, Integer value) {
        this.code = code;
        this.value = Optional.ofNullable(value).map(String::valueOf).orElse("");
    }
}
