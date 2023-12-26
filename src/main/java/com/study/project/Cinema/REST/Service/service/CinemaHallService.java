package com.study.project.Cinema.REST.Service.service;


import com.study.project.Cinema.REST.Service.dto.CinemaHallDto;
import com.study.project.Cinema.REST.Service.dto.CinemaStatsDto;
import com.study.project.Cinema.REST.Service.dto.PurchaseSeatResponseDto;
import com.study.project.Cinema.REST.Service.dto.SeatDto;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import com.study.project.Cinema.REST.Service.exception.AlreadyPurchasedException;
import com.study.project.Cinema.REST.Service.exception.NumberOutOfBoundsException;
import com.study.project.Cinema.REST.Service.exception.WrongPasswordException;
import com.study.project.Cinema.REST.Service.exception.WrongTokenException;
import com.study.project.Cinema.REST.Service.mapper.InformationMapper;
import com.study.project.Cinema.REST.Service.properties.CinemaProperties;
import com.study.project.Cinema.REST.Service.repository.SeatRepository;
import com.study.project.Cinema.REST.Service.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Data
@Service
@AllArgsConstructor
public class CinemaHallService {

    private final SeatRepository seatRepository;

    private final TicketRepository ticketRepository;

    private final CinemaProperties cinemaProperties;

    private final InformationMapper informationMapper;

    private static final String SECRET_PASSWORD = "super_secret";

    @Transactional
    public PurchaseSeatResponseDto purchaseSeat(SeatDto seatDto) {
        SeatEntity foundedSeat = seatRepository.findByRowNumAndColumnNum(seatDto.getRowNum(), seatDto.getColumnNum())
                .filter(fs -> {
                    if (fs.isOrdered()) {
                        throw new AlreadyPurchasedException();
                    }

                    return Boolean.TRUE;
                })
                .orElseThrow(NumberOutOfBoundsException::new);
        TicketEntity purchasedSeatTicket = TicketEntity.builder()
                .seat(foundedSeat)
                .build();

        foundedSeat.setOrdered(Boolean.TRUE);
        foundedSeat.setTicket(purchasedSeatTicket);

        return PurchaseSeatResponseDto.builder()
                .token(purchasedSeatTicket.getToken())
                .ticket(informationMapper.mapSeatEntityToDto(foundedSeat))
                .build();
    }

    @Transactional
    public SeatDto removePurchasedSeat(String token) {
        TicketEntity ticket = Optional.ofNullable(token)
                .flatMap(ticketRepository::findByToken)
                .orElseThrow(WrongTokenException::new);
        SeatEntity seat = ticket.getSeat()
                .toBuilder()
                .ordered(false)
                .ticket(null)
                .build();

        return informationMapper.mapSeatEntityToDto(seatRepository.save(seat));
    }

    public CinemaHallDto getCinemaHall() {
        List<SeatDto> allSeats = seatRepository.getAll()
                .stream()
                .map(informationMapper::mapSeatEntityToDto)
                .toList();

        return CinemaHallDto.builder()
                .columnsCount(cinemaProperties.getColumns())
                .rowsCount(cinemaProperties.getRows())
                .seats(allSeats)
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
                .filter(SeatEntity::isOrdered)
                .forEach(seat -> {
                    currentIncome.addAndGet(seat.getPrice());
                    purchasedSeats.incrementAndGet();
                });

        return CinemaStatsDto.builder()
                .currentIncome(currentIncome.get())
                .numberOfAvailableSeats(allSeats.size() - purchasedSeats.get())
                .numberOfPurchasedTickets(purchasedSeats.get())
                .build();
    }

    @PostConstruct
    private void createSeats() {
        IntStream.range(0, cinemaProperties.getColumns())
                .boxed()
                .forEach(this::createRowSeats);
    }

    private void createRowSeats(int columnNum) {
        IntStream.range(0, cinemaProperties.getRows())
                .forEach(rowNum -> {
                    if (seatRepository.findByRowNumAndColumnNum(rowNum, columnNum).isEmpty()) {
                        SeatEntity newSeat = SeatEntity.builder()
                                .rowNum(rowNum)
                                .columnNum(columnNum)
                                .ordered(false)
                                .build();

                        seatRepository.save(newSeat);
                    }
                });
    }

}
