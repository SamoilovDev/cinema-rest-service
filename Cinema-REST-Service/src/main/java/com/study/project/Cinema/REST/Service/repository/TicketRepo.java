package com.study.project.Cinema.REST.Service.repository;

import com.study.project.Cinema.REST.Service.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends CrudRepository<TicketEntity, Long> {
}
