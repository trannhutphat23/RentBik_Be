package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.InsuranceDto;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Model.Insurance;
import com.RentBikApp.RentBik.Model.SuccessResponse;
import com.RentBikApp.RentBik.Repository.CarRepository;
import com.RentBikApp.RentBik.Repository.InsuranceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final CarRepository carRepository;

    public InsuranceService(InsuranceRepository insuranceRepository, CarRepository carRepository) {
        this.insuranceRepository = insuranceRepository;
        this.carRepository = carRepository;
    }

    public Object addInsurance(InsuranceDto dto){
        var insurance = toInsurance(dto);

        // check mabh
        if (insuranceRepository.existsByMabh(insurance.getMabh())){
            return new ErrorResponse("Mabh must be unique");
        }

        return insuranceRepository.save(insurance);
    }

    public List<Insurance> findAllInsurance(){
        return insuranceRepository.findAllByOrderByIdAsc();
    }

    private Insurance toInsurance(InsuranceDto dto){
        var insurance = new Insurance();
        insurance.setMabh(dto.mabh());
        insurance.setPurchaseDate(dto.purchaseDate());
        insurance.setExpiredDate(dto.expiredDate());
        insurance.setPurchasePrice(dto.purchasePrice());
        return insurance;
    }

    public Object findInsuranceById(Integer id){
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(id);
        if (optionalInsurance.isEmpty()){
            return new ErrorResponse("Insurance doesn't exist");
        }

        return insuranceRepository.findById(id)
                .orElse(null);
    }

    public Object updateInsurance(InsuranceDto dto, Integer insurance_id){
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(insurance_id);
        if (optionalInsurance.isEmpty()){
            return new ErrorResponse("Insurance doesn't exist");
        }
        // update
        Insurance insurance = optionalInsurance.get();
        insurance.setMabh(dto.mabh());
        insurance.setPurchaseDate(dto.purchaseDate());
        insurance.setExpiredDate(dto.expiredDate());
        insurance.setPurchasePrice(dto.purchasePrice());
        // save
        return insuranceRepository.save(insurance);
    }

    public Object deleteInsurance(Integer id){
        Optional<Insurance> optionalInsurance = insuranceRepository.findById(id);
        if (optionalInsurance.isEmpty()){
            return new ErrorResponse("Insurance doesn't exist");
        }

        int count = carRepository.getCarHasInsuranceId(id);
        if (count > 0){
            return new ErrorResponse("This insurance is belongs to hiring car");
        }

        carRepository.updateCarHasInsuranceId(id);

        insuranceRepository.deleteInsuranceById(id);

        return new SuccessResponse("Delete successfully");
    }
}
