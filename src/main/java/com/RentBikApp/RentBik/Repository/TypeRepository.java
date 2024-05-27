package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}
