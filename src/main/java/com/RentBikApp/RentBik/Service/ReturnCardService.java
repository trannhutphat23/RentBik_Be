package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.HireInfoResponseDto;
import com.RentBikApp.RentBik.DTO.ReturnCardDto;
import com.RentBikApp.RentBik.DTO.ReturnCardResponseDto;
import com.RentBikApp.RentBik.Model.*;
import com.RentBikApp.RentBik.Repository.CarRepository;
import com.RentBikApp.RentBik.Repository.RentRepository;
import com.RentBikApp.RentBik.Repository.ReturnCardRepository;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReturnCardService {
    private final ReturnCardRepository returnCardRepository;
    private final RentRepository rentRepository;
    private final CarRepository carRepository;
    public ReturnCardService(ReturnCardRepository returnCardRepository, RentRepository rentRepository, CarRepository carRepository) {
        this.returnCardRepository = returnCardRepository;
        this.rentRepository = rentRepository;
        this.carRepository = carRepository;
    }

    public List<ReturnCardResponseDto> getReturnCards(){
        List<ReturnCard> returnCards = returnCardRepository.findAll();

        return returnCards.stream()
                .map(this::toReturnCardResponseDto)
                .collect(Collectors.toList());
    }

    public Object getHireInfoPrice(Integer customerId, Integer carId, LocalDate returnDate){
        LocalDate expiryDate = rentRepository.getExpiryDate(customerId, carId);
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()){
            Car car = optionalCar.get();
            long daysBetween = ChronoUnit.DAYS.between(expiryDate, returnDate);

            if (daysBetween > 0){
                // finePrice = 2%.hirePrice*days
                float finePrice = daysBetween*(car.getHirePrice()*2/100);
                float sumPrice = finePrice + car.getHirePrice();

                return new HireInfoResponseDto(finePrice, sumPrice);
            }else{
                float finePrice = 0;
                float sumPrice = car.getHirePrice();

                return new HireInfoResponseDto(finePrice, sumPrice);
            }
        }else {
            return new ErrorResponse("No exist customer or car");
        }
    }

    public Object addReturnCard(ReturnCardDto dto, Integer rentId){
        var returnCard = toReturnCard(dto);

        Optional<Rent> optionalRent = rentRepository.findById(rentId);
        if (optionalRent.isEmpty()){
            return new ErrorResponse("This rent doesn't exist");
        }

        Rent rent = optionalRent.get();
        returnCard.setRent(rent);
        rent.setRentStatus("Da thanh toan");

        Car car = rent.getCar();
        car.setStatus("Co san");

        return returnCardRepository.save(returnCard);
    }

    private ReturnCard toReturnCard(ReturnCardDto dto){
        var returnCard = new ReturnCard();
        returnCard.setFine(dto.finePrice());
        returnCard.setTotal(dto.total());
        returnCard.setReturnedDate(dto.returnDate());
        returnCard.setReturnNote(dto.returnNote());
        return returnCard;
    }

    private ReturnCardResponseDto toReturnCardResponseDto(ReturnCard returnCard){
        return new ReturnCardResponseDto(
                returnCard.getId(),
                returnCard.getRent(),
                returnCard.getRent().getCustomer(),
                returnCard.getReturnedDate(),
                returnCard.getFine(),
                returnCard.getTotal(),
                returnCard.getReturnNote()
        );
    }
}
