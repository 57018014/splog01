package com.yy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
/**
* 启动类1
*/
@SpringBootApplication
@MapperScan("com.yy.mapper")
public class SpringSqlLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSqlLogApplication.class, args);
    }
}
