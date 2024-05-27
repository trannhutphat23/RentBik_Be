package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.ReportCarDto;
import com.RentBikApp.RentBik.DTO.ReportCustomerDto;
import com.RentBikApp.RentBik.DTO.ReportRevenueDto;
import com.RentBikApp.RentBik.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final RentRepository rentRepository;
    private final ReturnCardRepository returnCardRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final InsuranceRepository insuranceRepository;

    public ReportService(CarRepository carRepository, CustomerRepository customerRepository, RentRepository rentRepository, ReturnCardRepository returnCardRepository, MaintenanceRepository maintenanceRepository, InsuranceRepository insuranceRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.rentRepository = rentRepository;
        this.returnCardRepository = returnCardRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public List<ReportCarDto> getReportCar(){
        List<Object[]> reportCars = carRepository.getReportCar();

        return reportCars.stream()
                .map(arr -> new ReportCarDto(
                        ((Number) arr[0]).intValue(),
                        (String) arr[1],
                        (String) arr[2],
                        (String) arr[3],
                        (String) arr[4],
                        ((Number) arr[5]).intValue(),
                        ((Number) arr[6]).intValue(),
                        ((Number) arr[7]).floatValue()
                ))
                .collect(Collectors.toList());
    }

    public List<ReportCustomerDto> getReportCustomer(){
        List<Object[]> reportCustomers = customerRepository.getReportCustomer();

        return reportCustomers.stream()
                .map(arr -> new ReportCustomerDto(
                        ((Number) arr[0]).intValue(),
                        (String) arr[1],
                        ((Number) arr[2]).intValue(),
                        ((Number) arr[3]).intValue(),
                        ((Number) arr[4]).intValue(),
                        ((Number) arr[5]).intValue(),
                        ((Number) arr[6]).floatValue()
                ))
                .collect(Collectors.toList());
    }

    public Object getReportRevenue(){
        Object totalHirePrice = rentRepository.getSumHirePrice();
        Object totalReturnPrice = returnCardRepository.getSumReturnPrice();
        Object totalMaintenancePrice = maintenanceRepository.getReportMaintenance();
        Object totalInsurancePrice = insuranceRepository.getReportInsurance();
        Float profit = ((Number) totalReturnPrice).floatValue() - ((Number) totalMaintenancePrice).floatValue() - ((Number) totalInsurancePrice).floatValue();

        return new ReportRevenueDto(
                ((Number) totalHirePrice).floatValue(),
                ((Number) totalReturnPrice).floatValue(),
                ((Number) totalMaintenancePrice).floatValue(),
                ((Number) totalInsurancePrice).floatValue(),
                profit
        );
    }
}
