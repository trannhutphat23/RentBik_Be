package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.CarResponseDto;
import com.RentBikApp.RentBik.DTO.RentDto;
import com.RentBikApp.RentBik.DTO.RentResponseDto;
import com.RentBikApp.RentBik.Model.Car;
import com.RentBikApp.RentBik.Model.Customer;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Model.Rent;
import com.RentBikApp.RentBik.Repository.CarRepository;
import com.RentBikApp.RentBik.Repository.CustomerRepository;
import com.RentBikApp.RentBik.Repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public RentService(RentRepository rentRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.rentRepository = rentRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    public Object addRent(RentDto dto, Integer carId, Integer customerId){
        var rent = toRent(dto);

        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCar.isEmpty() || optionalCustomer.isEmpty()){
            return new ErrorResponse("Car or customer not found");
        }

        Car car = optionalCar.get();
        Customer customer = optionalCustomer.get();

        car.setStatus("Khong co san");

        rent.setCar(car);
        rent.setCustomer(customer);

        return rentRepository.save(rent);
    }

    public List<RentResponseDto> findAllRent(){
        List<Rent> rents = rentRepository.findAll();

        return rents.stream()
                .map(this::toRentResponseDto)
                .collect(Collectors.toList());
    }

    public Object findHiringCarByCustomerId(Integer customerId){
        return customerId;
    }

    public Object findRentDetailInfo(String bsx, String cccd){
        // check car exist
        Car car = carRepository.findByBsx(bsx);
        if (car==null){
            return new ErrorResponse("Car doesn't exist");
        }

        // check customer exist
        Customer customer = customerRepository.findByCCCD(cccd);
        if (customer==null){
            return new ErrorResponse("Customer doesn't exist");
        }

        // check rent by cccd and bsx
        Rent rent = rentRepository.findRentInfoDetail(bsx, cccd);
        if (rent==null){
            return new ErrorResponse("Car hasn't already hired for this customer");
        }

        return toRentResponseDto(rentRepository.findRentInfoDetail(bsx, cccd));
    }

    private Rent toRent(RentDto dto){
        var rent = new Rent();

        rent.setExpiryDate(dto.expiredDate());
        rent.setRentalDate(dto.rentalDate());
        rent.setRentStatus("Dang thue");

        return rent;
    }

    private RentResponseDto toRentResponseDto(Rent rent){
        return new RentResponseDto(
                rent.getId(),
                toCarResponseDto(rent.getCar()),
                rent.getCustomer(),
                rent.getRentalDate(),
                rent.getExpiryDate(),
                rent.getRentalNote(),
                rent.getRentStatus(),
                rent.getReturnCard()
        );
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
