package com.thread_spring;

import com.thread_spring.async.Test;
import com.thread_spring.async.Test1;
import com.thread_spring.async.Test2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.Resource;

@EnableAsync
@SpringBootApplication
public class ThreadSpringDemoApplication implements ApplicationRunner {

    @Resource
    private Test test;

    @Resource
    private Test1 test1;

    @Resource
    private Test2 test2;

    public static void main(String[] args) {
        SpringApplication.run(ThreadSpringDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       test.test();
       test1.test();
       for(;;){
           Thread.yield();
           test2.test();
       }

    }
}
