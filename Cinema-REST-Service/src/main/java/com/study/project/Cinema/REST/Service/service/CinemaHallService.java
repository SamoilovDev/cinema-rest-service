package com.study.project.Cinema.REST.Service.service;


import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import com.study.project.Cinema.REST.Service.exceptions.HasBeenAlreadyPurchasedException;
import com.study.project.Cinema.REST.Service.exceptions.NumberOutOfBoundsException;
import com.study.project.Cinema.REST.Service.exceptions.WrongPasswordException;
import com.study.project.Cinema.REST.Service.exceptions.WrongTokenException;
import com.study.project.Cinema.REST.Service.repository.SeatRepo;
import com.study.project.Cinema.REST.Service.repository.TicketRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@Service
public class CinemaHallService {

    @Autowired
    private final SeatRepo seatRepo;

    @Autowired
    private final TicketRepo ticketRepo;

    public synchronized Map<String, Object> setPurchasedSeat(SeatEntity seatEntity) {
        SeatEntity seat = seatRepo.findById(seatEntity.getId())
                .orElseThrow(() -> new NumberOutOfBoundsException("The number of a row or a column is out of bounds!"));

        if (seat.getOrdered()) {
            throw new HasBeenAlreadyPurchasedException("The ticket has been already purchased!");
        } else {
            seat.setOrdered(true);
            seat.setTickets(new TicketEntity());

            return Map.of(
                    "token", seatRepo.save(seat).getTickets().getToken(),
                    "ticket", seat
            );
        }
    }

    public synchronized SeatEntity removePurchasedSeat(String token) {
        TicketEntity ticket = ticketRepo.findByToken(token)
                .orElseThrow(() -> new WrongTokenException("Wrong token!"));
        SeatEntity seat = ticket.getSeat();

        seat.setOrdered(false);
        seat.setTickets(null);
        return seatRepo.save(seat);
    }

    public Map<String, Number> getCinemaStats(String password) {
        if (!password.equals("super_secret")) throw new WrongPasswordException("The password is wrong!");

        List<SeatEntity> allSeats = (List<SeatEntity>) seatRepo.findAll();

        AtomicInteger currentIncome = new AtomicInteger(0);
        AtomicInteger purchasedSeats = new AtomicInteger(0);
        allSeats.stream()
                .filter(SeatEntity::getOrdered)
                .forEach(seat -> {
                    currentIncome.addAndGet(seat.getPrice());
                    purchasedSeats.incrementAndGet();
                });

        return Map.of(
                "current_income", currentIncome.get(),
                "number_of_available_seats", allSeats.size() - purchasedSeats.get(),
                "number_of_purchased_tickets", purchasedSeats.get()
        );
    }

}
