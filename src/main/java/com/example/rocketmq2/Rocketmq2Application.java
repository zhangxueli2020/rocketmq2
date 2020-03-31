package com.example.rocketmq2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Rocketmq2Application {

    public static void main(String[] args) {
        SpringApplication.run(Rocketmq2Application.class, args);
    }

}
