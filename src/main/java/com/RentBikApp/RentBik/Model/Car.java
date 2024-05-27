package com.RentBikApp.RentBik.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String licensePlate;

    @ManyToOne
    @JoinColumn(
            name = "brand_id",
            foreignKey = @ForeignKey(name = "fk_brand_car")
    )
    @JsonIgnore
    private Brand brand;

    @ManyToOne
    @JoinColumn(
            name = "series_id",
            foreignKey = @ForeignKey(name = "fk_series_car")
    )
    @JsonIgnore
    private Series series;

    @ManyToOne
    @JoinColumn(
            name = "type_id",
            foreignKey = @ForeignKey(name = "fk_type_car")
    )
    @JsonIgnore
    private Type type;
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "insurance_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_insurance_car"))
    @JsonIgnore
    private Insurance insurance;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Maintenance> maintenances;
    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Rent> rents;
    private Float purchasePrice;
    private LocalDate purchaseDate;
    private Float hirePrice;
    private String status = "Co san";
    private String carNote;

    public Car() {
    }

    public Car(Integer id, String licensePlate, Brand brand) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.brand = brand;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Float getHirePrice() {
        return hirePrice;
    }

    public void setHirePrice(Float hirePrice) {
        this.hirePrice = hirePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarNote() {
        return carNote;
    }

    public void setCarNote(String carNote) {
        this.carNote = carNote;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
