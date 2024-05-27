package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
//    boolean existsById(Integer id);
    @Query(nativeQuery = true,
            value = "SELECT t1.*, t2.license_plate " +
                    "FROM public.maintenance t1 " +
                    "INNER JOIN public.car t2 ON t1.car_id = t2.id " +
                    "WHERE (license_plate ILIKE %:keyword% OR maintenance_note ILIKE %:keyword% OR t1.status ILIKE %:keyword%)")
    List<Maintenance> findByKeywordContainingIgnoreCase(String keyword);

    // get report maintenance
    @Query(nativeQuery = true,
        value = "SELECT SUM(price) AS tien_bao_tri " +
                "FROM maintenance"
    )
    Object getReportMaintenance();

    // get record which equal car_id and status = 'Chua thanh toan'
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) " +
                    "FROM MAINTENANCE " +
                    "WHERE car_id = %:carId% AND status = 'Chua thanh toan'"
    )
    Integer getMaintenanceByCarId(Integer carId);

    // delete maintenance by car_id
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM maintenance " +
                    "WHERE car_id = %:carId%"
    )
    void deleteMaintenanceByCarId(Integer carId);

    // get list maintenance_id record which equal car_id
    @Query(nativeQuery = true,
            value = "SELECT id AS maintenance_id " +
                    "FROM maintenance " +
                    "WHERE car_id = %:carId%"
    )
    List<Integer> getListRentIdByCarId(Integer carId);
}
