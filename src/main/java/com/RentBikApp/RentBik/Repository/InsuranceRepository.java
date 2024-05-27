package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findAllByOrderByIdAsc();
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM insurance WHERE id = %:id%"
    )
    void deleteInsuranceById(Integer id);
    boolean existsByMabh(String mabh);

    @Query(nativeQuery = true,
        value = "SELECT SUM(purchase_price) AS tien_bao_hiem " +
                "FROM insurance"
    )
    Object getReportInsurance();
}
