package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.ReturnCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReturnCardRepository extends JpaRepository<ReturnCard, Integer> {
    @Query(nativeQuery = true,
            value = "select SUM(total) AS tien_tra_xe " +
                    "from return_card")
    Object getSumReturnPrice();

    // delete by rent_id
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM RETURN_CARD " +
                    "WHERE rent_id = %:rentId%"
    )
    void deleteByRentId(Integer rentId);
}
