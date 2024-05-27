package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.CarDto;
import com.RentBikApp.RentBik.DTO.CarResponseDto;
import com.RentBikApp.RentBik.DTO.CarUpdateDto;
import com.RentBikApp.RentBik.DTO.InsuranceCarDto;
import com.RentBikApp.RentBik.Service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars/add")
    public Object addCar(
            @RequestBody CarDto dto
    ){
        return carService.addCar(dto, dto.brandId(), dto.typeId(), dto.seriesId(), dto.insuranceId());
    }

    @GetMapping("/cars")
    public List<CarResponseDto> findAllCar(){
        return carService.findAllCar();
    }

    @GetMapping("/cars/{car_id}")
    public Object findCarById(
            @PathVariable("car_id") Integer id
    ){
        return carService.findCarById(id);
    }

    @GetMapping("/cars/search")
    public List<CarResponseDto> searchCars(
            @RequestParam String keyword
    ){
        return carService.searchCars(keyword);
    }

    @GetMapping("/cars/searchAvailable")
    public List<CarResponseDto> searchCarsAvailable(
            @RequestParam String keyword
    ){
        return carService.searchCarsAvailable(keyword);
    }

    @GetMapping("/cars/insuranceNotFound")
    public List<CarResponseDto> findCarNoInsurance(){
        return carService.findCarNoInsurance();
    }

    @GetMapping("/cars/available")
    public List<CarResponseDto> findCarAvailable(){
        return carService.findCarAvailable();
    }

    @PostMapping("/cars/add_insurance")
    public Object addInsurance(
            @RequestBody InsuranceCarDto dto
    ){
        return carService.addInsuranceForCar(dto.carId(), dto.insuranceId());
    }

    @PutMapping("/cars/update/{car_id}")
    public Object updateCar(
            @RequestBody CarUpdateDto dto,
            @PathVariable("car_id") Integer id
    ){
        return carService.updateCar(dto, id);
    }

    @DeleteMapping("/cars/delete/{car_id}")
    public Object deleteCar(
            @PathVariable("car_id") Integer id
    ){
        return carService.deleteCar(id);
    }
}
