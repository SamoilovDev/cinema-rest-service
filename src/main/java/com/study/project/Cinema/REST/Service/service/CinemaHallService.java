package com.study.project.Cinema.REST.Service.service;


import com.study.project.Cinema.REST.Service.dto.CinemaHallDto;
import com.study.project.Cinema.REST.Service.dto.CinemaStatsDto;
import com.study.project.Cinema.REST.Service.dto.PurchaseSeatResponseDto;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import com.study.project.Cinema.REST.Service.exceptions.AlreadyPurchasedException;
import com.study.project.Cinema.REST.Service.exceptions.NumberOutOfBoundsException;
import com.study.project.Cinema.REST.Service.exceptions.WrongPasswordException;
import com.study.project.Cinema.REST.Service.exceptions.WrongTokenException;
import com.study.project.Cinema.REST.Service.properties.CinemaProperties;
import com.study.project.Cinema.REST.Service.repository.SeatRepository;
import com.study.project.Cinema.REST.Service.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Data
@Service
@AllArgsConstructor
public class CinemaHallService {

    private final SeatRepository seatRepository;

    private final TicketRepository ticketRepository;

    private final CinemaProperties cinemaProperties;

    private static final String SECRET_PASSWORD = "super_secret";

    @Transactional
    public PurchaseSeatResponseDto setPurchasedSeat(SeatEntity seatEntity) {
        SeatEntity foundedSeat = seatRepository
                .findById(seatEntity.getId())
                .orElseThrow(NumberOutOfBoundsException::new);

        if (foundedSeat.getOrdered()) {
            throw new AlreadyPurchasedException();
        }

        SeatEntity orderedSeat = foundedSeat
                .toBuilder()
                .ordered(true)
                .ticket(new TicketEntity())
                .build();

        return PurchaseSeatResponseDto
                .builder()
                .token(
                        seatRepository
                                .save(foundedSeat)
                                .getTicket()
                                .getToken()
                )
                .ticket(orderedSeat)
                .build();
    }

    @Transactional
    public SeatEntity removePurchasedSeat(String token) {
        TicketEntity ticket = ticketRepository
                .findByToken(token)
                .orElseThrow(WrongTokenException::new);
        SeatEntity seat = ticket
                .getSeat()
                .toBuilder()
                .ordered(false)
                .ticket(null)
                .build();

        return seatRepository.save(seat);
    }

    public CinemaHallDto getCinemaHall() {
        return CinemaHallDto
                .builder()
                .columnsCount(cinemaProperties.getColumns())
                .rowsCount(cinemaProperties.getRows())
                .seats(seatRepository.getAll())
                .build();
    }

    public CinemaStatsDto getCinemaStats(String password) {
        if (!password.equals(SECRET_PASSWORD)) {
            throw new WrongPasswordException();
        }

        List<SeatEntity> allSeats = (List<SeatEntity>) seatRepository.findAll();
        AtomicInteger currentIncome = new AtomicInteger(0);
        AtomicInteger purchasedSeats = new AtomicInteger(0);

        allSeats.stream()
                .filter(SeatEntity::getOrdered)
                .forEach(seat -> {
                    currentIncome.addAndGet(seat.getPrice());
                    purchasedSeats.incrementAndGet();
                });

        return CinemaStatsDto
                .builder()
                .currentIncome(currentIncome.get())
                .numberOfAvailableSeats(allSeats.size() - purchasedSeats.get())
                .numberOfPurchasedTickets(purchasedSeats.get())
                .build();
    }

    @PostConstruct
    private void createSeats() {
        IntStream.range(0, cinemaProperties.getColumns())
                .boxed()
                .forEach(columnNum -> IntStream.range(0, cinemaProperties.getRows())
                        .forEach(rowNum -> {
                            if (seatRepository.findByRowNumAndColumnNum(rowNum, columnNum).isEmpty()) {
                                seatRepository.save(
                                        SeatEntity
                                                .builder()
                                                .rowNum(rowNum)
                                                .columnNum(columnNum)
                                                .ordered(false)
                                                .build()
                                );
                            }
                        })
                );
    }

}
