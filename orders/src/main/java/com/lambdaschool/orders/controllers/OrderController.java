package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder){

        newOrder.setOrdnum(0);
        newOrder = orderservices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/" + newOrder.getOrdnum())
            .build()
            .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/order/{ordnum}", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder,
                                             @PathVariable long ordnum){

        updateOrder.setOrdnum(ordnum);
        orderservices.save(updateOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum){

        orderservices.delete(ordnum);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
