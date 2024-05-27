package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record ReturnCardDto(
    Integer rentId,
    Float finePrice,
    Float total,
    LocalDate returnDate,
    String returnNote
) {
}
