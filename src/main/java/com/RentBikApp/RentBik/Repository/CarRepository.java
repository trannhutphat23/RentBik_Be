package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByOrderByIdAsc();
    boolean existsByLicensePlate(String licensePlate);
    @Query(nativeQuery = true,
            value = "SELECT t1.*, t3.name, t2.name " +
                    "FROM car t1 " +
                    "INNER JOIN series t2 ON t1.series_id = t2.id " +
                    "INNER JOIN type t3 ON t1.type_id = t3.id " +
                    "WHERE (license_plate ILIKE %:keyword% OR car_note ILIKE %:keyword% OR status ILIKE %:keyword% OR t2.name ILIKE %:keyword% OR t3.name ILIKE %:keyword%) " +
                    "ORDER BY t1.id ASC"
    )
    List<Car> findByKeywordContainingIgnoreCase(String keyword);

    @Query(nativeQuery = true,
            value = "SELECT t1.*, t3.name, t2.name " +
                    "FROM car t1 " +
                    "INNER JOIN series t2 ON t1.series_id = t2.id " +
                    "INNER JOIN type t3 ON t1.type_id = t3.id " +
                    "WHERE t1.status = 'Co san' AND (license_plate ILIKE %:keyword% OR t2.name ILIKE %:keyword% OR t3.name ILIKE %:keyword%) " +
                    "ORDER BY t1.id ASC")
    List<Car> searchCarsAvailable(String keyword);

    @Query(nativeQuery = true,
            value = "SELECT * FROM CAR " +
                    "WHERE insurance_id IS NULL ")
    List<Car> findCarHaveInsuranceNull();

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM CAR " +
                    "WHERE status = 'Co san' " +
                    "ORDER BY id ASC")
    List<Car> findCarAvailable();
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE CAR " +
                    "SET insurance_id = %:insuranceId% " +
                    "WHERE id = %:carId% ")
    void addNewInsurance(Integer carId, Integer insuranceId);

    @Query(nativeQuery = true,
          value = "SELECT * " +
                  "FROM CAR " +
                  "WHERE license_plate = %:bsx%")
    Car findByBsx(String bsx);

    @Query(nativeQuery = true,
            value = "SELECT t1.id, t1.license_plate, t4.mabh, t3.name as type_car, t2.name as series_car, " +
                    "COALESCE(t5.rent_count, 0) AS rent_count, COALESCE(t6.return_count, 0) AS return_count, COALESCE(t6.tong, 0) AS tong " +
                    "FROM CAR t1 " +
                    "INNER JOIN SERIES t2 ON t1.series_id = t2.id " +
                    "INNER JOIN TYPE t3 ON t1.type_id = t3.id " +
                    "LEFT JOIN INSURANCE t4 ON t1.insurance_id = t4.id " +
                    "LEFT JOIN (SELECT car_id, COUNT(*) AS rent_count FROM RENT GROUP BY car_id) t5 ON t1.id = t5.car_id " +
                    "LEFT JOIN ( " +
                    "SELECT car_id, COUNT(rent_id) AS return_count, SUM(tong_tien) AS tong " +
                    "FROM ( " +
                    "SELECT car_id, rent_id, COUNT(*) AS rent_count, COALESCE(SUM(total), 0) AS tong_tien " +
                    "FROM RENT " +
                    "LEFT JOIN return_card ON rent.id = return_card.rent_id " +
                    "GROUP BY car_id, rent_id " +
                    ") AS subquery " +
                    "GROUP BY car_id " +
                    ") t6 ON t1.id = t6.car_id " +
                    "ORDER BY t1.id ASC"
    )
    List<Object[]> getReportCar();

    // get record which match the insurance_id
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) " +
                    "FROM CAR " +
                    "WHERE insurance_id = %:id% AND status = 'Khong co san'"
    )
    Integer getCarHasInsuranceId(Integer id);

    // update car which match insurance_id
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE CAR " +
                    "SET insurance_id = NULL " +
                    "WHERE insurance_id = %:id% AND status = 'Co san'"
    )
    void updateCarHasInsuranceId(Integer id);

    // get insurance_id by car_id
    @Query(nativeQuery = true,
            value = "SELECT insurance_id " +
                    "FROM car " +
                    "WHERE id = %:carId%"
    )
    Optional<Integer> getInsuranceIdByCarId(Integer carId);
}
