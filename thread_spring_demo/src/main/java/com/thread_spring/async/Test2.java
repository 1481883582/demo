package com.thread_spring.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class Test2 {
    @Async
    public void test() {
        System.out.println("test方法,Test2类线程名："+Thread.currentThread().getName());
        msg();
    }

    private void msg(){
        System.out.println("msg方法,Test2类线程名："+Thread.currentThread().getName());
    }
}