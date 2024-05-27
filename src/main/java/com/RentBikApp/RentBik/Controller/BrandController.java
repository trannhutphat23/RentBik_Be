package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.DTO.BrandDto;
import com.RentBikApp.RentBik.Model.Brand;
import com.RentBikApp.RentBik.Service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class BrandController {
    private final BrandService brandService;
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public List<Brand> findAllBrands(){
        return brandService.findAllBrands();
    }

    @PostMapping("/brands/add")
    public Object addBrand(
            @RequestBody BrandDto dto
    ){
        return brandService.addBrand(dto);
    }

    @GetMapping("/brand/search/{brand_name}")
    public List<Brand> findBrandByName(
            @PathVariable("brand_name") String name
    ){
        return brandService.findBrandByName(name);
    }


}
