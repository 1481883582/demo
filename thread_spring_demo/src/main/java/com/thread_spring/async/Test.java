package com.thread_spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @Async
    public void test(){
        System.out.println("Test类线程名："+Thread.currentThread().getName());
    }
}