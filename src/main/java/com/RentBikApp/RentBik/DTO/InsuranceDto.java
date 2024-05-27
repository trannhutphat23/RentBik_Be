package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record InsuranceDto(
        String mabh,
        LocalDate purchaseDate,
        LocalDate expiredDate,
        Float purchasePrice
) {
}
