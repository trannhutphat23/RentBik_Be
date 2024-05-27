package com.RentBikApp.RentBik.Controller;

import com.RentBikApp.RentBik.Model.Account;
import com.RentBikApp.RentBik.Service.AccountService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account")
    public Account getAccount(){
        return accountService.getAccount();
    }

    @PutMapping("/account/change_password")
    public Object changePassword(
            @RequestParam String pin_code,
            @RequestParam String new_password
    ){
        return accountService.changePassword(pin_code, new_password);
    }
}
