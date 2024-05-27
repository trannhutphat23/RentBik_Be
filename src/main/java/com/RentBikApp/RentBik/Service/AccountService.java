package com.RentBikApp.RentBik.Service;

import com.RentBikApp.RentBik.Model.Account;
import com.RentBikApp.RentBik.Model.ErrorResponse;
import com.RentBikApp.RentBik.Model.SuccessResponse;
import com.RentBikApp.RentBik.Repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(){
        return accountRepository.getAccount();
    }

    public Object changePassword(String pin_code, String new_password){
        int count = accountRepository.getAccountByPinCode(pin_code);
        if (count == 0){
            return new ErrorResponse("Pin code doesn't match");
        }

        accountRepository.changePassword(new_password);

        return new SuccessResponse("Update successfully");
    }
}
