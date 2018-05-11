package com.synco.oa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication // 启动SpringBoot程序，而后自带子包扫描。
@EnableTransactionManagement
public class StartSpringBootMain {

	public static void main(String[] args) {

		SpringApplication.run(StartSpringBootMain.class, args);

	}

}
