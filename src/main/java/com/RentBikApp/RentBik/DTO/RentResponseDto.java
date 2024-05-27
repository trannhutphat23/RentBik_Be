package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Car;
import com.RentBikApp.RentBik.Model.Customer;
import com.RentBikApp.RentBik.Model.ReturnCard;

import java.time.LocalDate;

public record RentResponseDto(
        Integer id,
        CarResponseDto car,
        Customer customer,
        LocalDate rentalDate,
        LocalDate expiryDate,
        String rentalNote,
        String rentStatus,
        ReturnCard returnCard
) {
}
