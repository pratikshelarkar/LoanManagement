package com.management.risk_assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.management.risk_assessment.external")
@EnableDiscoveryClient
public class RiskAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiskAssessmentApplication.class, args);
	}

}
