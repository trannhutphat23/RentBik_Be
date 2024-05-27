package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {

    // get expire date by customer_id and car_id
    @Query(nativeQuery = true,
            value = "SELECT expiry_date FROM public.RENT " +
                    "WHERE customer_id = %:customerId% AND car_id = %:carId% AND rent_status = 'Dang thue'")
    LocalDate getExpiryDate(Integer customerId, Integer carId);

    // get info rent by bsx and cccd
    @Query(nativeQuery = true,
            value = "SELECT t1.*, t2.cccd, t3.status, t3.license_plate, t3.hire_price " +
                    "FROM public.RENT t1 " +
                    "INNER JOIN public.CUSTOMER t2 ON t1.customer_id = t2.id " +
                    "INNER JOIN public.CAR t3 ON t1.car_id = t3.id " +
                    "WHERE cccd = %:cccd% AND t3.license_plate = %:bsx% AND t3.status = 'Khong co san' AND t1.rent_status = 'Dang thue'")
    Rent findRentInfoDetail(String bsx, String cccd);

    // get sum of hire price
    @Query(nativeQuery = true,
            value = "SELECT SUM(thue_xe_goc) AS thue_xe_goc " +
                    "FROM ( " +
                    "SELECT car_id, SUM(hire_price) AS thue_xe_goc " +
                    "FROM public.CAR t1 " +
                    "INNER JOIN public.RENT t2 ON t1.id = t2.car_id " +
                    "GROUP BY car_id " +
                    ")")
    Object getSumHirePrice();

    // get record with equal param customer_id
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) " +
                    "FROM RENT " +
                    "WHERE customer_id = %:id% AND rent_status = 'Dang thue'"
    )
    Integer getRentHasCustomerId(Integer id);

    // get record which equal car_id and rent_status = 'Dang thue'
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) " +
                    "FROM RENT " +
                    "WHERE car_id = %:carId% AND rent_status = 'Dang thue'"
    )
    Integer getCarHasCarIdAndHiring(Integer carId);

    // get list rent_id record which equal car_id
    @Query(nativeQuery = true,
            value = "SELECT id AS rent_id " +
                    "FROM RENT " +
                    "WHERE car_id = %:carId%"
    )
    List<Integer> getListRentIdByCarId(Integer carId);
}
