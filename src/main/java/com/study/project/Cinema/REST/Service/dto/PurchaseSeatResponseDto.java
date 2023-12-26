package com.study.project.Cinema.REST.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseSeatResponseDto {

    private String token;

    private SeatDto ticket;

}
