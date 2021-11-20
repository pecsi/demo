package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MeasurementDTO {
        private double co2level;
        private LocalDateTime timestamp;
        private int districtId;
        private int cityId;
}
