package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.CarDto;
import com.RentBikApp.RentBik.DTO.CarResponseDto;
import com.RentBikApp.RentBik.DTO.CarUpdateDto;
import com.RentBikApp.RentBik.Model.*;
import com.RentBikApp.RentBik.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final SeriesRepository seriesRepository;
    private final InsuranceRepository insuranceRepository;
    private final RentRepository rentRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final ReturnCardRepository returnCardRepository;
    private final PaymentMaintenanceRepository paymentMaintenanceRepository;

    public CarService(CarRepository carRepository, TypeRepository typeRepository, BrandRepository brandRepository, SeriesRepository seriesRepository, InsuranceRepository insuranceRepository, RentRepository rentRepository, MaintenanceRepository maintenanceRepository, ReturnCardRepository returnCardRepository, PaymentMaintenanceRepository paymentMaintenanceRepository) {
        this.carRepository = carRepository;
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
        this.seriesRepository = seriesRepository;
        this.insuranceRepository = insuranceRepository;
        this.rentRepository = rentRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.returnCardRepository = returnCardRepository;
        this.paymentMaintenanceRepository = paymentMaintenanceRepository;
    }

    public Object addCar(CarDto dto, Integer brandId, Integer typeId, Integer seriesId, Integer insuranceId){
        var car = toCar(dto);

        // check license plate
        if (carRepository.existsByLicensePlate(dto.licensePlate())){
            return new ErrorResponse("License plate must be unique");
        }

        Optional<Type> optionalType = typeRepository.findById(typeId);
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (insuranceId != null){
            Optional<Insurance> optionalInsurance = insuranceRepository.findById(insuranceId);
            if (optionalInsurance.isPresent()){
                Insurance insurance = optionalInsurance.get();
                car.setInsurance(insurance);
                car.setHirePrice(dto.purchasePrice()*15/100);
            }
        }else{
            car.setHirePrice(dto.purchasePrice()*10/100);
        }

        if (optionalType.isPresent() && optionalBrand.isPresent() && optionalSeries.isPresent()){
            Type type = optionalType.get();
            Brand brand = optionalBrand.get();
            Series series = optionalSeries.get();

            car.setType(type);
            car.setBrand(brand);
            car.setSeries(series);
        }

        return carRepository.save(car);
    }

    private Car toCar(CarDto dto){
        var car = new Car();
        car.setLicensePlate(dto.licensePlate());
        car.setPurchasePrice(dto.purchasePrice());
        car.setPurchaseDate(dto.purchaseDate());
        car.setCarNote(dto.carNote());
        return car;
    }

    public List<CarResponseDto> findAllCar(){
        List<Car> cars = carRepository.findAllByOrderByIdAsc();

        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public Object findCarById(Integer id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()){
            return new ErrorResponse("Car doesn't exist");
        }

        return optionalCar.get();
    }

    public List<CarResponseDto> searchCars(String keyword){
        List<Car> cars = carRepository.findByKeywordContainingIgnoreCase(keyword);

        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> searchCarsAvailable(String keyword){
        List<Car> cars = carRepository.searchCarsAvailable(keyword);

        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> findCarNoInsurance(){
        List<Car> cars = carRepository.findCarHaveInsuranceNull();

        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public List<CarResponseDto> findCarAvailable(){
        List<Car> cars = carRepository.findCarAvailable();

        return cars.stream()
                .map(this::toCarResponseDto)
                .collect(Collectors.toList());
    }

    public Object addInsuranceForCar(Integer carId, Integer insuranceId){

        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(insuranceId);

        if (optionalCar.isEmpty() || optionalInsurance.isEmpty()){
            return new ErrorResponse("Car or insurance not found");
        }

        Car car = optionalCar.get();
        car.setHirePrice(car.getPurchasePrice()*15/100);
        carRepository.addNewInsurance(carId, insuranceId);


        return new SuccessResponse("Add insurance successfully");
    }

    public Object updateCar(CarUpdateDto dto, Integer id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()){
            return new ErrorResponse("Car doesn't exist");
        }
        // update
        Car car = optionalCar.get();
        car.setLicensePlate(dto.licensePlate());
        car.setPurchaseDate(dto.purchaseDate());
        car.setPurchasePrice(dto.purchasePrice());
        car.setCarNote(dto.carNote());
        // update hire price
        Optional<Integer> optionalInsuranceId = carRepository.getInsuranceIdByCarId(id);
        if (optionalInsuranceId.isPresent()){
            car.setHirePrice(dto.purchasePrice()*15/100);
        }else{
            car.setHirePrice((dto.purchasePrice()*10/100));
        }
        //save
        return carRepository.save(car);
    }

    public Object deleteCar(Integer id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isEmpty()){
            return new ErrorResponse("Car doesn't exist");
        }

        int count = rentRepository.getCarHasCarIdAndHiring(id);
        if (count > 0){
            return new ErrorResponse("This car is hiring");
        }

        int countMaintenance = maintenanceRepository.getMaintenanceByCarId(id);
        if (countMaintenance > 0){
            return new ErrorResponse("Have not already payment for this car");
        }

        List<Integer> rentIds = rentRepository.getListRentIdByCarId(id);
        for (Integer rentId : rentIds){
            returnCardRepository.deleteByRentId(rentId);
            rentRepository.deleteById(rentId);
        }
        List<Integer> maintenanceIds = maintenanceRepository.getListRentIdByCarId(id);
        for (Integer maintenanceId : maintenanceIds){
            paymentMaintenanceRepository.deleteByMaintenanceId(maintenanceId);
            maintenanceRepository.deleteById(maintenanceId);
        }
        carRepository.deleteById(id);

        return new SuccessResponse("Delete successfully");
    }

    private CarResponseDto toCarResponseDto(Car car){
        return new CarResponseDto(
                car.getId(),
                car.getLicensePlate(),
                car.getType(),
                car.getBrand(),
                car.getSeries(),
                car.getInsurance(),
                car.getPurchasePrice(),
                car.getHirePrice(),
                car.getPurchaseDate(),
                car.getCarNote(),
                car.getStatus()
        );
    }
}
