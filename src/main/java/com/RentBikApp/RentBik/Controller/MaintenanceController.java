package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.*;
import com.RentBikApp.RentBik.Service.MaintenanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/maintenances/add")
    public Object addMaintenance(
            @RequestBody MaintenanceDto dto
    ){
        return maintenanceService.addMaintenance(dto, dto.carId());
    }

    @GetMapping("/maintenances")
    public List<MaintenanceResponseDto> findAllMaintenance(){
        return maintenanceService.findAllMaintenance();
    }

    @GetMapping("/maintenances/search")
    public List<MaintenanceResponseDto> searchMaintenances(
            @RequestParam String keyword
    ){
        return maintenanceService.searchMaintenances(keyword);
    }

    @PostMapping("/maintenances/pay")
    public Object payMaintenance(
        @RequestBody PaymentMaintenanceDto dto
    ){
        return maintenanceService.payMaintenance(dto, dto.maintenanceId());
    }
}
