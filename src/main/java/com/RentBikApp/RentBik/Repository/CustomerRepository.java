package com.RentBikApp.RentBik.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RentBikApp.RentBik.Model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByOrderByIdAsc();
    boolean existsByCccd(String cccd);
    boolean existsByPhoneNumber(String phoneNumber);
    @Query(nativeQuery = true,
            value = "SELECT t1.*, t3.rank " +
                    "FROM public.customer t1 " +
                    "INNER JOIN public.customer_gplx t2 ON t1.id = t2.customer_id " +
                    "INNER JOIN public.gplx t3 ON t2.gplx_id = t3.id " +
                    "WHERE (t1.cccd ILIKE %:cccd%)" +
                    "LIMIT 1"
    )
    Customer findAllByCccdContaining(String cccd);
    @Query(nativeQuery = true,
        value = "SELECT t1.*, t3.rank FROM public.customer t1 " +
                "INNER JOIN customer_gplx t2 ON t1.id = t2.customer_id " +
                "INNER JOIN gplx t3 ON t2.gplx_id = t3.id " +
                "WHERE t1.cccd ILIKE %:keyword% " +
                "OR t1.fullname ILIKE %:keyword% " +
                "OR t1.note ILIKE %:keyword% " +
                "OR t1.phone_number ILIKE %:keyword% " +
                "OR t3.rank ILIKE %:keyword% " +
                "ORDER BY t1.id ASC"
    )
    List<Customer> findByKeywordContainingIgnoreCase(String keyword);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM public.CUSTOMER " +
                    "WHERE cccd = %:cccd% ")
    Customer findByCCCD(String cccd);

    @Query(nativeQuery = true,
            value = "SELECT t1.id, t1.fullname, COUNT(DISTINCT t2.id) AS rent_count, COUNT(DISTINCT t3.rent_id) AS return_count, " +
                    "COUNT(DISTINCT CASE WHEN t2.rent_status = 'Dang thue' THEN t2.id END) AS dang_thue_count, " +
                    "COUNT(DISTINCT CASE WHEN t2.rent_status = 'Da thanh toan' THEN t2.id END) AS da_thanh_toan_count, " +
                    "COALESCE(SUM(t3.tong), 0) AS total_sum " +
                    "FROM public.CUSTOMER t1 " +
                    "LEFT JOIN public.RENT t2 ON t1.id = t2.customer_id " +
                    "LEFT JOIN ( " +
                    "SELECT rent_id, SUM(tong_tien) AS tong " +
                    "FROM ( " +
                    "SELECT rent_id, COUNT(*) AS rent_count, COALESCE(SUM(total), 0) AS tong_tien " +
                    "FROM public.RETURN_CARD " +
                    "GROUP BY rent_id " +
                    ") " +
                    "GROUP BY rent_id " +
                    ") t3 ON t2.id = t3.rent_id " +
                    "GROUP BY t1.id, t1.fullname " +
                    "ORDER BY t1.id;"
    )
    List<Object[]> getReportCustomer();

    @Query(nativeQuery = true,
        value = "SELECT id, cccd FROM CUSTOMER " +
                "ORDER BY id ASC"
    )
    List<Object[]> getCccds();

    // get record not equal param cccd
    @Query(nativeQuery = true,
        value = "SELECT COUNT(*) " +
                "FROM CUSTOMER " +
                "WHERE phone_number = %:phoneNumber% AND cccd != %:cccd%"
    )
    Integer getCustomerNotCccd(String phoneNumber, String cccd);
}
