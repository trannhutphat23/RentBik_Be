package com.RentBikApp.RentBik.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "car_id",
            foreignKey = @ForeignKey(name = "fk_rent_car")
    )
    @JsonBackReference
    private Car car;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_rent_customer")
    )
    @JsonBackReference
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate expiryDate;
    private String rentalNote;
    private String rentStatus;
    @OneToOne(mappedBy = "rent")
    @JsonManagedReference
    private ReturnCard returnCard;

    public Rent() {
    }

    public Rent(Integer id, Car car, Customer customer, LocalDate rentalDate, LocalDate expiryDate, String rentalNote) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.expiryDate = expiryDate;
        this.rentalNote = rentalNote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRentalNote() {
        return rentalNote;
    }

    public void setRentalNote(String rentalNote) {
        this.rentalNote = rentalNote;
    }

    public String getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }

    public ReturnCard getReturnCard() {
        return returnCard;
    }

    public void setReturnCard(ReturnCard returnCard) {
        this.returnCard = returnCard;
    }

}
