package com.RentBikApp.RentBik.DTO;

public record ReportCustomerDto(
        Integer makh,
        String name,
        Integer rents,
        Integer returns,
        Integer hiring_cars,
        Integer returned_cars,
        Float sum_rent
) {
}
