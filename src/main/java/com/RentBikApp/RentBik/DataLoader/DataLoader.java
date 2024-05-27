package com.RentBikApp.RentBik.DataLoader;

import com.RentBikApp.RentBik.Model.Account;
import com.RentBikApp.RentBik.Model.Gplx;
import com.RentBikApp.RentBik.Model.Type;
import com.RentBikApp.RentBik.Repository.AccountRepository;
import com.RentBikApp.RentBik.Repository.GplxRepository;
import com.RentBikApp.RentBik.Repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final GplxRepository gplxRepository;
    private final TypeRepository typeRepository;
    private List<String> gplxs = List.of("A1", "A2", "A3", "A4", "B1", "B2", "C", "D", "E", "F");
    private List<String> types = List.of("Xe máy", "Xe đạp", "Ô tô");

    @Autowired
    public DataLoader(AccountRepository accountRepository, GplxRepository gplxRepository, TypeRepository typeRepository) {
        this.accountRepository = accountRepository;
        this.gplxRepository = gplxRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            Account defaultAccount = new Account();
            defaultAccount.setPassword("admin");
            accountRepository.save(defaultAccount);
        }

        if (gplxRepository.count() == 0){
            for (String item : gplxs){
                Gplx gplx = new Gplx();
                gplx.setRank(item);
                gplxRepository.save(gplx);
            }
        }

        if (typeRepository.count() == 0){
            for (String item : types){
                Type type = new Type();
                type.setName(item);
                typeRepository.save(type);
            }
        }
    }
}
