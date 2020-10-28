package com.college.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author winte
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.college")
@MapperScan("com.college.aclservice.mapper")

public class ServiceAclApplication {
    public static void main(String[] args) {

        SpringApplication.run(ServiceAclApplication.class, args);
    }

}
