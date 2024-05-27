package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record CarDto(
        String licensePlate,
        Integer typeId,
        Integer brandId,
        Integer seriesId,
        Integer insuranceId,
        Float purchasePrice,
        LocalDate purchaseDate,
        String carNote
) {
}
