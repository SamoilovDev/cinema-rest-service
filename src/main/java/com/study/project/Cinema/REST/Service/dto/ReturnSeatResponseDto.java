package com.study.project.Cinema.REST.Service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnSeatResponseDto {

    @JsonProperty("returned_seat")
    private SeatDto returnedSeat;

}

