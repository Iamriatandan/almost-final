package com.billing.billing_servic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BillingServicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServicApplication.class, args);
	}

}
