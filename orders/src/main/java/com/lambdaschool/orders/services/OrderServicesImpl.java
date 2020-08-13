package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomerRepo;
import com.lambdaschool.orders.repositories.OrderRepo;
import com.lambdaschool.orders.repositories.PaymentRepo;
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

    @Autowired
    PaymentRepo paymentrepo;

    @Autowired
    CustomerRepo customerrepo;

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

        Order newOrder = new Order();

        if(order.getOrdnum() != 0){

            orderrepo.findById(order.getOrdnum())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Order " + order.getOrdnum() + " Not Found."));

            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());
        newOrder.setCustomer(customerrepo.findById(order.getCustomer()
            .getCustcode())
        .orElseThrow(() -> new EntityNotFoundException(
            "Customer " + order.getCustomer().getCustcode() + " Not Found.")));

        newOrder.getPayments().clear();

        for(Payment p: order.getPayments()){

            Payment newPay = paymentrepo.findById(p.getPaymentid())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Payment " + p.getPaymentid() + " Not Found."));

            newOrder.getPayments().add(newPay);
        }
        return orderrepo.save(newOrder);
    }

    @Transactional
    @Override
    public void delete(long id) {

        orderrepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
            "Order " + id + " Not Found."
        ));
        orderrepo.deleteById(id);
    }
}
