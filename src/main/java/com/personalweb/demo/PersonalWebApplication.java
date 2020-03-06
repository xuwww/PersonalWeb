package com.personalweb.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.personalweb.demo.mapper")
public class PersonalWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalWebApplication.class, args);
    }

}
