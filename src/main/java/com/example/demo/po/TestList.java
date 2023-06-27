package com.example.demo.po;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


@Data
public class TestList {

    @Size(max = 3)
    private List<Integer> list;
}
