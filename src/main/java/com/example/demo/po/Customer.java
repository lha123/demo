package com.example.demo.po;

import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author magang
 * @since 2019-11-18
 */


@Data
public class Customer<T>{

    T data;




}
