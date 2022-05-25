package com.fullcycle.catalogovideo.application;

import com.fullcycle.catalogovideo.application.configuration.WebServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class CatalogoVideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServerConfig.class, args);
	}

}
