package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Gplx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GplxRepository extends JpaRepository<Gplx, Integer> {
    List<Gplx> findAllByRankContaining(String rank);

    @Query(nativeQuery = true,
        value = "SELECT * " +
                "FROM GPLX " +
                "WHERE rank = %:rank%"
    )
    Integer getGplxByRank(String rank);
}
