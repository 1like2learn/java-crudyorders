package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "paymentService")
public class PaymentServicesImpl implements PaymentServices {

    @Autowired
    PaymentRepo paymentrepo;

    @Override
    public Payment save(Payment payment){
        return paymentrepo.save(payment);
    }
}
