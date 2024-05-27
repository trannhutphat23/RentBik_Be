package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAllByNameContaining(String name);
    boolean existsByName(String name);
}
