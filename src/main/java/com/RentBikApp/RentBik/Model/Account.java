package com.RentBikApp.RentBik.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email = "RentBik@gmail.com";
    private String password;
    private String PINCode = "RENTBIK";

    public Account(){}

    public Account(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPINCode() {
        return PINCode;
    }

    public void setPINCode(String PINCode) {
        this.PINCode = PINCode;
    }
}
