package com.RentBikApp.RentBik.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ReturnCard")
public class ReturnCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "rent_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_rent_car"))
    @JsonBackReference
    private Rent rent;

    private LocalDate returnedDate;
    private Float fine;
    private Float total;
    private String returnNote;

    public ReturnCard(){}

    public ReturnCard(Integer id, Rent rent, LocalDate returnedDate, Float fine, Float total, String returnNote) {
        this.id = id;
        this.rent = rent;
        this.returnedDate = returnedDate;
        this.fine = fine;
        this.total = total;
        this.returnNote = returnNote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Float getFine() {
        return fine;
    }

    public void setFine(Float fine) {
        this.fine = fine;
    }

    public String getReturnNote() {
        return returnNote;
    }

    public void setReturnNote(String returnNote) {
        this.returnNote = returnNote;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
