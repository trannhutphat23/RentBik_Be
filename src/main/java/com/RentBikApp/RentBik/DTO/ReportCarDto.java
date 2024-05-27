package com.RentBikApp.RentBik.DTO;

public record ReportCarDto(
        Integer id,
        String license_plate,
        String mabh,
        String type_car,
        String series_car,
        Integer rent_count,
        Integer return_count,
        Float sum
) {
}
