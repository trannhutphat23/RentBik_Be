package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.PaymentMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PaymentMaintenanceRepository extends JpaRepository<PaymentMaintenance, Integer> {
    // delete by maintenance_id
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM payment_maintenance " +
                    "WHERE maintenance_id = %:maintenanceId%"
    )
    void deleteByMaintenanceId(Integer maintenanceId);
}
