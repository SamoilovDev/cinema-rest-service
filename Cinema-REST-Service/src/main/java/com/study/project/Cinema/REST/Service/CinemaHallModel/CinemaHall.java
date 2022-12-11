package com.study.project.Cinema.REST.Service.CinemaHallModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.project.Cinema.REST.Service.CinemaHallService.CinemaHallService;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PrePersist;
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
    @JsonProperty("available_seats")
    List<SeatEntity> seats;

    private final CinemaHallService cinemaHallService;

    public CinemaHall(@Autowired CinemaHallService cinemaHallService) {
        this.cinemaHallService = cinemaHallService;
    }


    @PostConstruct
    public void init() {
        this.seats = IntStream.rangeClosed(1, rows)
                .boxed()
                .flatMap(row -> IntStream.rangeClosed(1, columns)
                        .boxed()
                        .map(column -> {
                            SeatEntity seat;
                            if (cinemaHallService.getSeatRepo().findByRowNumAndColumnNum(row, column).isEmpty()) {
                                seat = new SeatEntity();
                                seat.setColumnNum(column);
                                seat.setRowNum(row);
                                seat.setIsOrdered(false);
                                cinemaHallService.getSeatRepo().save(seat);
                            } else {
                                seat = (SeatEntity) cinemaHallService
                                        .getSeatRepo()
                                        .findByRowNumAndColumnNum(row, column).get();
                            }
                            return seat;
                        }))
                .collect(Collectors.toList());
    }

}
