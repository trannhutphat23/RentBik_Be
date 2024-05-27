package com.RentBikApp.RentBik.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gplx")
public class Gplx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String rank;
    @ManyToMany(
            mappedBy = "gplxs"
    )
    @JsonManagedReference
    private Set<Customer> customer = new HashSet<Customer>();
    public Gplx() {
    }

    public Gplx(Integer id, String rank) {
        this.id = id;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
    public Set<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Set<Customer> customer) {
        this.customer = customer;
    }
}
