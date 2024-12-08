package com.loan.loan_manage;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.loan.loan_manage.exceptions.MyException;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.loan.loan_manage.external")
@EnableDiscoveryClient
public class LoanManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanManageApplication.class, args);
	}

	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	@Bean
	MyException myException() {
		return new MyException();
	}
}
