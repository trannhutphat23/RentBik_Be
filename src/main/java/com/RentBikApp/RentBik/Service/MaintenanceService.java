package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.*;
import com.RentBikApp.RentBik.Model.Car;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Model.Maintenance;
import com.RentBikApp.RentBik.Model.PaymentMaintenance;
import com.RentBikApp.RentBik.Repository.CarRepository;
import com.RentBikApp.RentBik.Repository.MaintenanceRepository;
import com.RentBikApp.RentBik.Repository.PaymentMaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final CarRepository carRepository;
    private final PaymentMaintenanceRepository paymentMaintenanceRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, CarRepository carRepository, PaymentMaintenanceRepository paymentMaintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.carRepository = carRepository;
        this.paymentMaintenanceRepository = paymentMaintenanceRepository;
    }

    public Object addMaintenance(MaintenanceDto dto, Integer carId){
        var maintenance = toMaintenance(dto);

        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            maintenance.setCar(car);
        }

        return maintenanceRepository.save(maintenance);
    }

    public List<MaintenanceResponseDto> findAllMaintenance(){
        List<Maintenance> maintenances = maintenanceRepository.findAll();

        return maintenances.stream()
                .map(this::toMaintenanceResponseDto)
                .collect(Collectors.toList());
    }

    public List<MaintenanceResponseDto> searchMaintenances(String keyword){
        List<Maintenance> maintenances = maintenanceRepository.findByKeywordContainingIgnoreCase(keyword);

        return maintenances.stream()
                .map(this::toMaintenanceResponseDto)
                .collect(Collectors.toList());
    }

    public Object payMaintenance(PaymentMaintenanceDto dto, Integer maintenanceId){
        var paymentMaintenance = toPaymentMaintenance(dto);

        // check maintenance
        if (!maintenanceRepository.existsById(maintenanceId)){
            return new ErrorResponse("Maintenance is don't exists");
        }

        Optional<Maintenance> optionalMaintenance = maintenanceRepository.findById(maintenanceId);
        if (optionalMaintenance.isPresent()){
            Maintenance maintenance = optionalMaintenance.get();

            // check input price is bigger exist price
            if (dto.price() > maintenance.getPrice()){
                return new ErrorResponse("Payment price should not bigger than maintenance price");
            }

            // tru tien thanh toan
            maintenance.setPrice(maintenance.getPrice() - dto.price());

            // check price = 0
            if (maintenance.getPrice() == 0){
                maintenance.setStatus("Da thanh toan");
            }

            maintenanceRepository.save(maintenance);

            paymentMaintenance.setMaintenance(maintenance);
        }

        return paymentMaintenanceRepository.save(paymentMaintenance);
    }

    private Maintenance toMaintenance(MaintenanceDto dto){
        var maintenance = new Maintenance();
        maintenance.setMaintenanceDate(dto.maintenanceDate());
        maintenance.setMaintenanceNote(dto.maintenanceNote());
        maintenance.setPrice(dto.price());
        return maintenance;
    }

    private PaymentMaintenance toPaymentMaintenance(PaymentMaintenanceDto dto){
        var paymentMaintenance = new PaymentMaintenance();
        paymentMaintenance.setPaymentDate(dto.paymentDate());
        paymentMaintenance.setMoneys(dto.price());
        return paymentMaintenance;
    }

    private MaintenanceResponseDto toMaintenanceResponseDto(Maintenance maintenance){
        return new MaintenanceResponseDto(
                maintenance.getId(),
                maintenance.getCar(),
                maintenance.getMaintenanceDate(),
                maintenance.getMaintenanceNote(),
                maintenance.getStatus(),
                maintenance.getPrice()
        );
    }
}
