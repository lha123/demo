package com.example.demo.Aop;

import com.example.demo.po.DogShow1;
import com.example.demo.po.TestAa;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class DogService implements DogApi{


    @Override
    public TestAa show(TestAa a) {
        return new TestAa();
    }

    @Override
    public DogShow1 show1(List<String> a) {
        System.out.println("山东省地方");
        return new DogShow1();
    }
}
