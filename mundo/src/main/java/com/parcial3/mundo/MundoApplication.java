package com.parcial3.mundo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MundoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MundoApplication.class, args);
	}

}
