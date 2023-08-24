package com.study.project.Cinema.REST.Service.dto;

import com.study.project.Cinema.REST.Service.entity.SeatEntity;
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

    private SeatEntity ticket;

}
