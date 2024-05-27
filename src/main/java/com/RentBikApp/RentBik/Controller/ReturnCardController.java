package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.HireInfoDto;
import com.RentBikApp.RentBik.DTO.ReturnCardDto;
import com.RentBikApp.RentBik.DTO.ReturnCardResponseDto;
import com.RentBikApp.RentBik.Service.ReturnCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ReturnCardController {
    private final ReturnCardService returnCardService;

    public ReturnCardController(ReturnCardService returnCardService) {
        this.returnCardService = returnCardService;
    }

    @PostMapping("/return_cards/hire_info_price")
    public Object getHireInfoPrice(
            @RequestBody HireInfoDto dto
    ){
        return returnCardService.getHireInfoPrice(dto.customerId(), dto.carId(), dto.returnDate());
    }

    @PostMapping("/return_cards/add")
    public Object addReturnCard(
            @RequestBody ReturnCardDto dto
    ){
        return returnCardService.addReturnCard(dto, dto.rentId());
    }

    @GetMapping("/return_cards")
    public List<ReturnCardResponseDto> getReturnCards(){
        return returnCardService.getReturnCards();
    }
}
