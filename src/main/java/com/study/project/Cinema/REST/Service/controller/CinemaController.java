package com.study.project.Cinema.REST.Service.controller;

import com.study.project.Cinema.REST.Service.dto.CinemaHallDto;
import com.study.project.Cinema.REST.Service.dto.CinemaStatsDto;
import com.study.project.Cinema.REST.Service.dto.PurchaseSeatResponseDto;
import com.study.project.Cinema.REST.Service.dto.RequestDto;
import com.study.project.Cinema.REST.Service.dto.ReturnSeatResponseDto;
import com.study.project.Cinema.REST.Service.service.CinemaHallService;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CinemaController {

    private final CinemaHallService cinemaHallService;

    @GetMapping("/seats")
    public ResponseEntity<CinemaHallDto> getAllSeatsAtRoom() {
        return ResponseEntity.ok(cinemaHallService.getCinemaHall());
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchaseSeatResponseDto> buyTicket(@RequestBody SeatEntity seatEntity) {
        return ResponseEntity.ok(cinemaHallService.setPurchasedSeat(seatEntity));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnSeatResponseDto> returnTicket(@RequestBody RequestDto request) {
        SeatEntity returnedSeat = cinemaHallService.removePurchasedSeat(request.getRequestStringField());
        return ResponseEntity.ok(
                ReturnSeatResponseDto
                        .builder()
                        .returnedSeat(returnedSeat)
                        .build()
        );
    }

    @PostMapping("/stats")
    public ResponseEntity<CinemaStatsDto> getStatistics(@RequestBody RequestDto request) {
        return ResponseEntity.ok(
                cinemaHallService.getCinemaStats(request.getRequestStringField())
        );
    }

}
