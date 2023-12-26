package com.study.project.Cinema.REST.Service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.study.project.Cinema.REST.Service.entity.SeatEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link SeatEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatDto implements Serializable {

    private Integer rowNum;

    private Integer columnNum;

    private Integer price;

    private Boolean ordered;
}