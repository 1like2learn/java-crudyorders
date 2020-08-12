package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrderRepo orderrepo;

    @Override
    public Order findOrderById(Long orderid) {

        return orderrepo.findById(orderid)
            .orElseThrow(() -> new EntityNotFoundException("Order " + orderid + " not found."));
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> list = new ArrayList<>();
        orderrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public Order save(Order order){
        return orderrepo.save(order);
    }

}
