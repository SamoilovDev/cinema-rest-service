package com.study.project.Cinema.REST.Service.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ticket")
@NoArgsConstructor
@Data
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private SeatEntity seat;

    @PostConstruct
    private void init() {
        token = UUID.randomUUID().toString();
    }

}
