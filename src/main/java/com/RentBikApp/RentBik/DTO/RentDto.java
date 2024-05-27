package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record RentDto(
        Integer carId,
        Integer customerId,
        LocalDate expiredDate,
        LocalDate rentalDate
) {
}
