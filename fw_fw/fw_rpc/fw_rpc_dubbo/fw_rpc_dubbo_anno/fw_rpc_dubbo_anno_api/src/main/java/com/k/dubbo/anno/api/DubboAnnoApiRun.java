package com.k.dubbo.anno.api;

import com.alibaba.csp.sentinel.util.AppNameUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@ImportResource("spring-anno.xml")
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class DubboAnnoApiRun {
    public static void main(String[] args) {
//        System.setProperty(AppNameUtil.APP_NAME,"consumer.dubbo");
        SpringApplication.run(DubboAnnoApiRun.class, args);
    }
}
