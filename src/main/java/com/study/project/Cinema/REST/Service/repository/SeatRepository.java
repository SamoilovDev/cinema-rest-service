package com.study.project.Cinema.REST.Service.repository;

import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends CrudRepository<SeatEntity, Long> {

    Optional<SeatEntity> findByRowNumAndColumnNum(Integer rowNum, Integer columnNum);

    List<SeatEntity> getAll();

}
