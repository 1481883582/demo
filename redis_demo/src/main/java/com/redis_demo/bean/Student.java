package com.redis_demo.bean;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
@Builder
@ToString
public class Student implements Serializable {
    private String name;
    private int age;
}
