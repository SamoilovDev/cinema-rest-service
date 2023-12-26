package com.study.project.Cinema.REST.Service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat")
@Builder(toBuilder = true)
public class SeatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "row_num", nullable = false)
    private Integer rowNum;

    @Column(name = "column_num", nullable = false)
    private Integer columnNum;

    @Column(name = "price")
    private Integer price = rowNum < 4 ? 8 : 10;

    @Column(name = "is_ordered")
    private boolean ordered;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "seat")
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    private TicketEntity ticket;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeatEntity that = (SeatEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
