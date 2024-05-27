package com.RentBikApp.RentBik.DTO;

import com.RentBikApp.RentBik.Model.Gplx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record CustomerDto(
        String cccd,
        String fullname,
        String birthday,
        String phoneNumber,
        Set<Integer> gplxIds,
        String note
) {
}
