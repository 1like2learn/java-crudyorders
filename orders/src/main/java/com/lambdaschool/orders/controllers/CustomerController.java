package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.services.OrderServices;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerservices;
    @Autowired
    private OrderServices orderservices;

    //http://localhost:2019/customers/orders
    //give a list of all customers and their orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> findAllCustomers(){

        List<Customer> myList = customerservices.findAllCustomers();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/7
    //give a specific customer and their orders
    @GetMapping(value = "/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable Long custid){

        Customer thisCustomer = customerservices.findCustomerById(custid);
        return new ResponseEntity<>(thisCustomer, HttpStatus.OK);
    }

    //http://localhost:2019/customers/namelike/mes
    //give all customers that have a pathVariable provided substring somewhere in their names
    @GetMapping(value = "/namelike/{substring}", produces = "application/json")
    public ResponseEntity<?> findCustomersBySubstring(@PathVariable String substring){

        List<Customer> myList = customerservices.findCustomersBySubstring(substring);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/orders/count
    //give all customers and the number of orders they've made
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getOrderCount(){

        List<OrderCounts> myList = customerservices.getOrderCount();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer){

        newCustomer.setCustcode(0);
        newCustomer = customerservices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/" + newCustomer.getCustcode())
            .build()
            .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/customer/{custcode}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer,
                                            @PathVariable long custcode){

        customerservices.update(updateCustomer, custcode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
