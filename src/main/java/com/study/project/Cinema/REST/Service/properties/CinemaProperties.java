package com.study.project.Cinema.REST.Service.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@ConfigurationProperties(prefix = "cinema.room.total")
@PropertySource(value = "classpath:application.properties")
public class CinemaProperties {

    private int rows;

    private int columns;
}
