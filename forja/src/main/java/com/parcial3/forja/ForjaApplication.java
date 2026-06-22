package com.parcial3.forja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ForjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForjaApplication.class, args);
	}

}
