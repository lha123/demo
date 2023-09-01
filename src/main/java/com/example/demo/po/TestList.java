package com.example.demo.po;


import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;


@Data
public class TestList {

    @Size(max = 3)
    private List<Integer> list;
}
