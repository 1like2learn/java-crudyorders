package com.lambdaschool.orders.repositories;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    List<Customer> findByCustnameContainingIgnoringCase(String substring);

    @Query(value = "SELECT c.custname AS name, count(ordnum) AS countorders " +
        "FROM customers c LEFT JOIN orders o " +
        "ON c.custcode = o.custcode " +
        "GROUP BY c.custname " +
        "ORDER BY countorders DESC", nativeQuery = true)
    List<OrderCounts> findOrderCounts();
}
