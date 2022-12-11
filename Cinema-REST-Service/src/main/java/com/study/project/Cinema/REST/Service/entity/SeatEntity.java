package com.study.project.Cinema.REST.Service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@NoArgsConstructor
@Data
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private Integer rowNum;

    @Column(unique = true)
    private Integer columnNum;

    private Integer price;

    @JsonIgnore
    private Boolean isOrdered;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seat")
    private TicketEntity tickets;

    @PostConstruct
    private void init() {
        price = rowNum < 4 ? 8 : 10;
    }
}
