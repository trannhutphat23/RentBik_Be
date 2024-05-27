package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Rent;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record CustomerResponseDto(
        Integer id,
        String cccd,
        String fullname,
        String birthday,
        String phoneNumber,
        Set<GplxResponseDto> gplxes,
        List<RentResponseDto> rents,
        String note
) {
}
