package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServices orderservices;

    //http://localhost:2019/orders/order/7
    //gives a specific order
    @GetMapping(value = "/order/{orderid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable Long orderid){

        Order thisOrder = orderservices.findOrderById(orderid);
        return new ResponseEntity<>(thisOrder, HttpStatus.OK);
    }


    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers(){

        List<Order> myList = orderservices.findAllOrders();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

}
