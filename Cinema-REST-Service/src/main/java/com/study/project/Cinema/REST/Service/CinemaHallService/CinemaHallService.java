package com.study.project.Cinema.REST.Service.CinemaHallService;

import com.study.project.Cinema.REST.Service.CinemaHallModel.CinemaHall;
import com.study.project.Cinema.REST.Service.Exceptions.HasBeenAlreadyPurchasedException;
import com.study.project.Cinema.REST.Service.Exceptions.NumberOutOfBoundsException;
import com.study.project.Cinema.REST.Service.Exceptions.WrongPasswordException;
import com.study.project.Cinema.REST.Service.Exceptions.WrongTokenException;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import com.study.project.Cinema.REST.Service.repository.SeatRepo;
import com.study.project.Cinema.REST.Service.repository.TicketRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Getter
public class CinemaHallService {

    private final SeatRepo seatRepo;

    private final TicketRepo ticketRepo;

    public CinemaHallService(@Autowired SeatRepo seatRepo, TicketRepo ticketRepo) {
        this.seatRepo = seatRepo;
        this.ticketRepo = ticketRepo;
    }


    public synchronized Map<String, Object> setPurchasedSeat(SeatEntity seatEntity) {
        if (seatRepo.findById(seatEntity.getId()).isPresent()) {
            if (seatEntity.getIsOrdered()) {

                throw new HasBeenAlreadyPurchasedException("The ticket has been already purchased!");
            } else {
                seatEntity.setIsOrdered(true);
                TicketEntity ticket = new TicketEntity();
                ticket.setSeat(seatEntity);
                ticketRepo.save(ticket);

                return Map.of("token", ticket.getToken(), "ticket", seatEntity);
            }
        } else throw new NumberOutOfBoundsException("The number of a row or a column is out of bounds!");
    }

    public synchronized SeatEntity removePurchasedSeat(String token) {

        List<SeatEntity> seatWithToken = new ArrayList<>();
        seatRepo.findAll().forEach(seatEntity -> {
            if (seatEntity.getIsOrdered() &&
                    seatEntity.getTickets()
                            .getToken()
                            .equals(token)) {
                seatWithToken.add(seatEntity);
            }
                });

        if (seatWithToken.isEmpty()) {
            throw new WrongTokenException("Wrong token!");
        } else {
            seatWithToken.get(0).setIsOrdered(false);
            return seatWithToken.get(0);
        }
    }

    public Map<String, Number> getCinemaStats(String password) {
        if (! password.equals("super_secret")) throw new WrongPasswordException("The password is wrong!");

        List<SeatEntity> allSeats = (List<SeatEntity>) seatRepo.findAll();

        AtomicInteger currentIncome = new AtomicInteger(0);
        allSeats.stream()
                .filter(SeatEntity::getIsOrdered)
                .forEach(seat -> currentIncome.addAndGet(seat.getPrice()));

        Long availableSeats = allSeats.stream()
                .filter(seat -> ! seat.getIsOrdered())
                .count();

        Long purchasedSeats = allSeats.stream()
                .filter(SeatEntity::getIsOrdered)
                .count();

        return Map.of("current_income", currentIncome.get(),
                "number_of_available_seats", availableSeats,
                "number_of_purchased_tickets", purchasedSeats);
    }

}
