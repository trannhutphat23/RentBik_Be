package com.RentBikApp.RentBik.Model;

//import com.RentBikApp.RentBik.DTO.PaymentMaintenanceDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Payment_Maintenance")
public class PaymentMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(
            name = "maintenance_id",
            foreignKey = @ForeignKey(name = "fk_maintenance_payment")
    )
    @JsonBackReference
    private Maintenance maintenance;

    private LocalDate paymentDate;

    private Float moneys;

    public PaymentMaintenance(){}

    public PaymentMaintenance(Integer id, Maintenance maintenance, LocalDate paymentDate, Float moneys) {
        this.id = id;
        this.maintenance = maintenance;
        this.paymentDate = paymentDate;
        this.moneys = moneys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Float getMoneys() {
        return moneys;
    }

    public void setMoneys(Float moneys) {
        this.moneys = moneys;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
    }
}
