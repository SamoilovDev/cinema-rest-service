package com.study.project.Cinema.REST.Service.controllers;

import com.study.project.Cinema.REST.Service.model.CinemaHall;
import com.study.project.Cinema.REST.Service.service.CinemaHallService;
import com.study.project.Cinema.REST.Service.exceptions.*;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@AllArgsConstructor
@RestController
public class CinemaController {

    @Autowired
    private final CinemaHallService cinemaHallService;

    @Autowired
    private final CinemaHall cinemaHall;

    @GetMapping("/seats")
    public ResponseEntity<CinemaHall> getAllSeatsAtRoom() {
        return ResponseEntity.ok(cinemaHall);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> buyTicket(@RequestBody SeatEntity seatEntity)
            throws NumberOutOfBoundsException, HasBeenAlreadyPurchasedException {

        try {
            Map<String, Object> seatInfo = new TreeMap<>(Comparator.reverseOrder());
            seatInfo.putAll(cinemaHallService.setPurchasedSeat(seatEntity));

            return ResponseEntity.ok(seatInfo);
        } catch (NumberOutOfBoundsException | HasBeenAlreadyPurchasedException ex) {

            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnTicket(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        try {
            SeatEntity returnedSeat = cinemaHallService.removePurchasedSeat(token);
            return ResponseEntity.ok(Map.of("returned_ticket", returnedSeat));
        } catch (WrongTokenException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> getStatistics(@RequestBody Map<String, String> request) {
        String password = request.get("password");

        try {
            Map<String, Number> statistics = new TreeMap<>(Comparator.naturalOrder());
            statistics.putAll(cinemaHallService.getCinemaStats(password));

            return ResponseEntity.ok(statistics);
        } catch (WrongPasswordException ex) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(401))
                    .body(Map.of("error", ex.getMessage()));
        }
    }

}
