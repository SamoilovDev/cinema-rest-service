package com.study.project.Cinema.REST.Service.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@ConfigurationProperties(prefix = "cinema.room.total")
@PropertySource(value = "classpath:application.properties")
public class CinemaProperties {

    @Value("${cinema.room.total.rows}")
    private int rows;

    @Value("${cinema.room.total.columns}")
    private int columns;

}
