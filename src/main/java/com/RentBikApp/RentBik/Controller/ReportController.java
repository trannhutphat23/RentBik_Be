package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.ReportCarDto;
import com.RentBikApp.RentBik.DTO.ReportCustomerDto;
import com.RentBikApp.RentBik.Service.ReportService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports/cars")
    public List<ReportCarDto> getReportCar(){
        return reportService.getReportCar();
    }

    @GetMapping("/reports/customers")
    public List<ReportCustomerDto> getReportCustomer(){
        return reportService.getReportCustomer();
    }

    @GetMapping("/reports/revenue")
    public Object getReportRevenue(){
        return reportService.getReportRevenue();
    }
}
