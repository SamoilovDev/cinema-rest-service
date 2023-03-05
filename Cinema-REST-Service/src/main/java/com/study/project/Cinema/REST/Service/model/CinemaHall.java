package com.study.project.Cinema.REST.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.project.Cinema.REST.Service.service.CinemaHallService;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Component
public class CinemaHall {

    @Value("${cinema.room.total.rows}")
    @JsonProperty("total_rows")
    private int rows;

    @Value("${cinema.room.total.rows}")
    @JsonProperty("total_columns")
    private int columns;

    @JsonProperty("all_seats")
    List<SeatEntity> seats;

    @Autowired
    private final CinemaHallService cinemaHallService;


    @PostConstruct
    public void init() {
        this.seats = IntStream.rangeClosed(1, rows)
                .boxed()
                .flatMap(
                        row -> IntStream.rangeClosed(1, columns)
                                .boxed()
                                .map(
                                        column -> cinemaHallService.getSeatRepo()
                                                .findByRowNumAndColumnNum(row, column)
                                                .orElseGet(
                                                        () -> SeatEntity.builder()
                                                                .columnNum(column)
                                                                .rowNum(row)
                                                                .ordered(false)
                                                                .build()
                                                )
                                )
                )
                .collect(Collectors.toList());
    }

}
