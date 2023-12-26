package com.study.project.Cinema.REST.Service.mapper;

import com.study.project.Cinema.REST.Service.dto.SeatDto;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InformationMapper {

    public SeatDto mapSeatEntityToDto(SeatEntity seatEntity) {
        return Optional.ofNullable(seatEntity)
                .map(se -> SeatDto.builder()
                        .price(se.getPrice())
                        .ordered(se.isOrdered())
                        .rowNum(se.getRowNum())
                        .columnNum(se.getColumnNum())
                        .build())
                .orElse(null);
    }

}
