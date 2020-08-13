package com.lambdaschool.orders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    /*
    * fields:
    * custcode
    * custname
    * custcity
    * workingarea
    * custcountry
    * grade
    * openingamt
    * receiveamt
    * paymentamt
    * outstandingamt
    * phone
    * foreign key: agent
    * foreign field: payments
    * */
    //dim primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long custcode;

    //dim name
    @Column(nullable = false)
    private String custname;

    //dim city
    private String custcity;

    //dim working area
    private String workingarea;

    //dim country
    private String custcountry;

    //dim grade
    private String grade;

    //dim opening amount
    private double  openingamt;

    //dim receive amount
    private double receiveamt;

    //dim payment amount
    private double paymentamt;

    //dim outstanding amount
    private double outstandingamt;

    //dim phone
    private String phone;

    //connect the customers table to the agents table in a many to one relation
    @ManyToOne()
    @JoinColumn(name = "agentcode", nullable = false)
    @JsonIgnoreProperties("customers")
    private Agent agent;

    //connect customers to orders table in a one to many relation
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "customer")
    private List<Order> orders = new ArrayList<>();


    //Constructors
    public Customer() {
    }

    public Customer(String custcity, String custcountry, String custname, String grade, double openingamt, double outstandingamt, double paymentamt, String phone, double receiveamt, String workingarea, Agent agent) {
        this.custcity = custcity;
        this.custcountry = custcountry;
        this.custname = custname;
        this.grade = grade;
        this.openingamt = openingamt;
        this.outstandingamt = outstandingamt;
        this.paymentamt = paymentamt;
        this.phone = phone;
        this.receiveamt = receiveamt;
        this.workingarea = workingarea;
        this.agent = agent;
    }

    public long getCustcode() {
        return custcode;
    }

    public void setCustcode(long custcode) {
        this.custcode = custcode;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getOpeningamt() {
        return openingamt;
    }

    public void setOpeningamt(double openingamt) {
        this.openingamt = openingamt;
    }

    public double getReceiveamt() {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt) {
        this.receiveamt = receiveamt;
    }

    public double getPaymentamt() {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt) {
        this.paymentamt = paymentamt;
    }

    public double getOutstandingamt() {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt) {
        this.outstandingamt = outstandingamt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
