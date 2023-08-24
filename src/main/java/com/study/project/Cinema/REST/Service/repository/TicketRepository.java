package com.study.project.Cinema.REST.Service.repository;

import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByToken(String token);

}
