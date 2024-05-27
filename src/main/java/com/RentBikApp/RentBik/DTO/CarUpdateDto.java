package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record CarUpdateDto(
        String licensePlate,
        LocalDate purchaseDate,
        Float purchasePrice,
        String carNote
) {
}
