package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Integer> {
    boolean existsByName(String name);
}
