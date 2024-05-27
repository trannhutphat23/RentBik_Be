package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.DTO.BrandDto;
import com.RentBikApp.RentBik.Model.Brand;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }
    public List<Brand> findAllBrands(){
        return brandRepository.findAll();
    }

    public Object addBrand(BrandDto dto){
        var brand = toBrand(dto);

        // check name brand
        if (brandRepository.existsByName(brand.getName())){
            return new ErrorResponse("Name must be unique");
        }

        return brandRepository.save(brand);
    }
    private Brand toBrand(BrandDto dto){
        var brand = new Brand();
        brand.setName(dto.name());
        return brand;
    }
    public List<Brand> findBrandByName(String name){
        return brandRepository.findAllByNameContaining(name);
    }
}
