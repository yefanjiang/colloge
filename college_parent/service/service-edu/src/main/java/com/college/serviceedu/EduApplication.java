package com.college.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author winte
 * 设置启动类，注意位置要放在根包，与其他几个业务包并列。
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.college"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
