package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PAYMENTS")
public class Payment {

    //dim primary key payment id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long paymentid;

    //dim type
    private String type;

    @ManyToMany(mappedBy = "payments")
    @JsonIgnoreProperties("payments")
    private Set<Order> orders = new HashSet<>();

    //constructors
    public Payment() {
    }

    public Payment(String type) {
        this.type = type;
    }

    /*=== getters and setters ===*/

    //payment id
    public long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(long paymentid) {
        this.paymentid = paymentid;
    }

    //type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //orders
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
