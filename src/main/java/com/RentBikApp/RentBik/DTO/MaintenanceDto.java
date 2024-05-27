package com.RentBikApp.RentBik.DTO;

import java.time.LocalDate;

public record MaintenanceDto(
    Integer carId,
    LocalDate maintenanceDate,
    String maintenanceNote,
    Float price
) {
}
