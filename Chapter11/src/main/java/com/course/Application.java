package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PreDestroy;
//@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication
//@ComponentScan("com.course")
public class Application {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        com.course.Application.context= SpringApplication.run(com.course.Application.class,args);
//        SpringApplication.run(Application.class,args);
    }
    @PreDestroy
    public void close(){
        com.course.Application.context.close();
    }
}
