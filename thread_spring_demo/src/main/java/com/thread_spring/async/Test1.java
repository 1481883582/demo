package com.thread_spring.async;

import org.springframework.stereotype.Component;

@Component
public class Test1 {
    public void test() {
        System.out.println("Test1类线程名："+Thread.currentThread().getName());
    }
}