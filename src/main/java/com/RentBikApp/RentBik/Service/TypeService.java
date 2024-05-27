package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.Model.Type;
import com.RentBikApp.RentBik.Repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> findAllTypes(){
        return typeRepository.findAll();
    }
}
