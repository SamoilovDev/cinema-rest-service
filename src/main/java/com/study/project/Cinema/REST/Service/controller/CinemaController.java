package com.study.project.Cinema.REST.Service.controller;

import com.study.project.Cinema.REST.Service.dto.CinemaHallDto;
import com.study.project.Cinema.REST.Service.dto.CinemaStatsDto;
import com.study.project.Cinema.REST.Service.dto.PurchaseSeatResponseDto;
import com.study.project.Cinema.REST.Service.dto.AccessCredentialsDto;
import com.study.project.Cinema.REST.Service.dto.ReturnSeatResponseDto;
import com.study.project.Cinema.REST.Service.dto.SeatDto;
import com.study.project.Cinema.REST.Service.service.CinemaHallService;
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
    public ResponseEntity<PurchaseSeatResponseDto> purchaseSeat(@RequestBody SeatDto seatDto) {
        return ResponseEntity.ok(cinemaHallService.purchaseSeat(seatDto));
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnSeatResponseDto> returnTicket(@RequestBody AccessCredentialsDto request) {
        SeatDto returnedSeat = cinemaHallService.removePurchasedSeat(request.getToken());
        return ResponseEntity.ok(new ReturnSeatResponseDto(returnedSeat));
    }

    @PostMapping("/stats")
    public ResponseEntity<CinemaStatsDto> getStatistics(@RequestBody AccessCredentialsDto request) {
        return ResponseEntity.ok(
                cinemaHallService.getCinemaStats(request.getPassword())
        );
    }

}
