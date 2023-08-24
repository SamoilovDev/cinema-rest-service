package com.study.project.Cinema.REST.Service;

import com.study.project.Cinema.REST.Service.properties.CinemaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CinemaProperties.class)
public class CinemaRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaRestServiceApplication.class, args);
	}

}

