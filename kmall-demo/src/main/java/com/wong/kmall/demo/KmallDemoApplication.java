package com.wong.kmall.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author KaKinWong
 * @crate_time 2021/7/27 20:10
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wong.kmall")
@MapperScan({"com.wong.kmall.mapper","com.wong.kmall.demo.dao"})
public class KmallDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(KmallDemoApplication.class, args);
    }
}
