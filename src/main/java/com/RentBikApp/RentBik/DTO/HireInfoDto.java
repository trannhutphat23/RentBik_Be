package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record HireInfoDto(
        Integer customerId,
        Integer carId,
        LocalDate returnDate
) {

}
