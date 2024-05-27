package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Brand;
import com.RentBikApp.RentBik.Model.Insurance;
import com.RentBikApp.RentBik.Model.Series;
import com.RentBikApp.RentBik.Model.Type;

import java.time.LocalDate;

public record CarResponseDto(
        Integer id,
        String licensePlate,
        Type type,
        Brand brand,
        Series series,
        Insurance insurance,
        Float purchasePrice,
        Float hirePrice,
        LocalDate purchaseDate,
        String carNote,
        String status
) {
}
