package com.RentBikApp.RentBik.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Maintenance")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "car_id",
            foreignKey = @ForeignKey(name = "fk_maintenance_car")
    )
    @JsonBackReference
    private Car car;
    private LocalDate maintenanceDate;
    private String maintenanceNote;
    private Float price;
    private String status = "Chua thanh toan";

    @OneToMany(mappedBy = "maintenance")
    @JsonManagedReference
    private List<PaymentMaintenance> paymentMaintenances;

    public Maintenance() {
    }

    public Maintenance(Integer id, Car car, LocalDate maintenanceDate, String maintenanceNote) {
        this.id = id;
        this.car = car;
        this.maintenanceDate = maintenanceDate;
        this.maintenanceNote = maintenanceNote;
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

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getMaintenanceNote() {
        return maintenanceNote;
    }

    public void setMaintenanceNote(String maintenanceNote) {
        this.maintenanceNote = maintenanceNote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
