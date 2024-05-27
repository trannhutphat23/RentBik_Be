package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record PaymentMaintenanceDto(
        Integer maintenanceId,
        LocalDate paymentDate,
        Float price
) {
}
