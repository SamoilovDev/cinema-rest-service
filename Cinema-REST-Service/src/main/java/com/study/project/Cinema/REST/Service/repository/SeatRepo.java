package com.study.project.Cinema.REST.Service.repository;

import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepo extends CrudRepository<SeatEntity, Long> {
    Optional<SeatRepo> findByRowNumAndColumnNum(Integer row_num, Integer column_num);
}
