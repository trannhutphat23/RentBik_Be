package com.RentBikApp.RentBik.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String mabh;
    private LocalDate purchaseDate;
    private LocalDate expiredDate;
    private Float purchasePrice;

    @OneToOne(mappedBy = "insurance")
    private Car car;
    public Insurance() {
    }

    public Insurance(Integer id, String mabh, LocalDate purchaseDate, LocalDate expiredDate, Float purchasePrice) {
        this.id = id;
        this.mabh = mabh;
        this.purchaseDate = purchaseDate;
        this.expiredDate = expiredDate;
        this.purchasePrice = purchasePrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMabh() {
        return mabh;
    }

    public void setMabh(String mabh) {
        this.mabh = mabh;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
