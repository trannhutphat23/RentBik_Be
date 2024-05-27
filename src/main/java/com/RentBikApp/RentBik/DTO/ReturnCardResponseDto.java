package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Customer;
import com.RentBikApp.RentBik.Model.Rent;

import java.time.LocalDate;

public record ReturnCardResponseDto(
        Integer id,
        Rent rent,
        Customer customer,
        LocalDate returnedDate,
        Float fine,
        Float total,
        String returnNote
) {
}
