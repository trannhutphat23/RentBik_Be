package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.Model.Type;
import com.RentBikApp.RentBik.Service.TypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TypeController {
    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/types")
    public List<Type> findAllTypes(){
        return typeService.findAllTypes();
    }
}
