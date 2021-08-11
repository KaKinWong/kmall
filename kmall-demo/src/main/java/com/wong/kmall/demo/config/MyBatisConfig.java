package com.wong.kmall.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author KaKinWong
 * @crate_time 2021/7/24 23:11
 * @description MyBatis配置
 */
@Configuration
@MapperScan({"com.wong.kmall.mapper","com.wong.kmall.demo.dao"})
public class MyBatisConfig {
}
