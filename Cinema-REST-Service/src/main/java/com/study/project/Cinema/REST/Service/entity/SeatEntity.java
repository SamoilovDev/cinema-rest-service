package com.study.project.Cinema.REST.Service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "seat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty(value = "row_num")
    @Column(name = "row_num", nullable = false)
    private Integer rowNum;

    @JsonProperty(value = "column_num")
    @Column(name = "column_num", nullable = false)
    private Integer columnNum;

    @Column
    private Integer price;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean ordered;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seat")
    private TicketEntity tickets;

    @PostConstruct
    private void init() {
        price = rowNum < 4 ? 8 : 10;
    }

    @JsonIgnore
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeatEntity that = (SeatEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @JsonIgnore
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
