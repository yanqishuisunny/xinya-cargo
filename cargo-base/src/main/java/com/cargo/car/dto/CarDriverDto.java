package com.cargo.car.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarDriverDto {
    private String carId;
    private List<String> carIds;
    private List<String> listDriverIds;
}
