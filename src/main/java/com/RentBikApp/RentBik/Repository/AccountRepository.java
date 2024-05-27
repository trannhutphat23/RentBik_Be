package com.RentBikApp.RentBik.Repository;

import com.RentBikApp.RentBik.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    // get account
    @Query(nativeQuery = true,
            value = "SELECT * FROM ACCOUNT "
    )
    Account getAccount();

    // get record which equal pin_code
    @Query(nativeQuery = true,
        value = "SELECT COUNT(*) " +
                "FROM ACCOUNT " +
                "WHERE pincode = %:pin_code%"
    )
    Integer getAccountByPinCode(String pin_code);

    // change password
    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE ACCOUNT " +
                    "SET password = %:pass_word%"
    )
    void changePassword(String pass_word);
}
