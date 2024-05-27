package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.InsuranceDto;
import com.RentBikApp.RentBik.Model.Insurance;
import com.RentBikApp.RentBik.Service.InsuranceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class InsuranceController {
    private final InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @PostMapping("/insurances/add")
    public Object addInsurance(
            @RequestBody InsuranceDto dto
    ){
        return insuranceService.addInsurance(dto);
    }
    @GetMapping("/insurances")
    public List<Insurance> findAllInsurance(){
        return insuranceService.findAllInsurance();
    }
    @GetMapping("/insurances/{insurance_id}")
    public Object findInsuranceById(
            @PathVariable("insurance_id") Integer id
    ){
        return insuranceService.findInsuranceById(id);
    }

    @PutMapping("/insurances/update/{insurance_id}")
    public Object updateInsurance(
            @RequestBody InsuranceDto dto,
            @PathVariable("insurance_id") Integer id
    ){
        return insuranceService.updateInsurance(dto, id);
    }

    @DeleteMapping("/insurances/delete/{insurance_id}")
    public Object deleteInsurance(
            @PathVariable("insurance_id") Integer id
    ){
        return insuranceService.deleteInsurance(id);
    }
}
