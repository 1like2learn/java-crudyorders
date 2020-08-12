package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    /*
    * ordnum
    * ordamount
    * advanceamount
    * orderdescription
    * foreign key: custcode
    * shared table: orderspayments
    * */
    //dim order number as the primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long ordnum;

    //dim order amount
    private double ordamount;

    //dim advance amount
    private double advanceamount;

    //dim order description
    private String orderdescription;

    @ManyToOne
    @JoinColumn(name = "custcode", nullable = false)
    @JsonIgnoreProperties("orders")
    private Customer customer;

    @ManyToMany()
    @JoinTable(name = "orderspayments",
    joinColumns = @JoinColumn(name = "ordnum"),
    inverseJoinColumns = @JoinColumn(name = "paymentid"))
    @JsonIgnoreProperties("orders")
    Set<Payment> payments = new HashSet<>();

    //constructors
    public Order() {
    }

    public Order(
        double ordamount,
        double advanceamount,
        Customer customer,
        String orderdescription) {
        this.ordamount = ordamount;
        this.customer = customer;
        this.advanceamount = advanceamount;
        this.orderdescription = orderdescription;
    }

    /*=== getters and setters ===*/

    public long getOrdnum() {
        return ordnum;
    }

    public void setOrdnum(long ordnum) {
        this.ordnum = ordnum;
    }

    public double getOrdamount() {
        return ordamount;
    }

    public void setOrdamount(double ordamount) {
        this.ordamount = ordamount;
    }

    public double getAdvanceamount() {
        return advanceamount;
    }

    public void setAdvanceamount(double advanceamount) {
        this.advanceamount = advanceamount;
    }

    public String getOrderdescription() {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription) {
        this.orderdescription = orderdescription;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment){
        this.payments.add(payment);
    }
}
