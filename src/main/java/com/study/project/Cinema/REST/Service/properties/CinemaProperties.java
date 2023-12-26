package com.study.project.Cinema.REST.Service.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "cinema.room.total")
public class CinemaProperties {

    private int rows;

    private int columns;

}
