package com.study.project.Cinema.REST.Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CinemaHallDto {

    @JsonProperty("columns_count")
    private int columnsCount;

    @JsonProperty("rows_count")
    private int rowsCount;

    private List<SeatEntity> seats;

}