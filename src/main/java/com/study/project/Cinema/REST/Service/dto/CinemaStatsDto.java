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
public class CinemaStatsDto {

    @JsonProperty("current_income")
    private Integer currentIncome;

    @JsonProperty("number_of_available_seats")
    private Integer numberOfAvailableSeats;

    @JsonProperty("number_of_purchased_tickets")
    private Integer numberOfPurchasedTickets;

}
