package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Car;

import java.time.LocalDate;

public record MaintenanceResponseDto(
        Integer id,
        Car car,
        LocalDate maintenanceDate,
        String maintenanceNote,
        String status,
        Float price
) {
}
