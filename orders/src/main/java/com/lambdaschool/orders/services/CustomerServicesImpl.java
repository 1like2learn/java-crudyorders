package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomerRepo;
import com.lambdaschool.orders.repositories.PaymentRepo;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServicesImpl implements CustomerServices {

    @Autowired
    private CustomerRepo customerrepo;

    @Autowired
    private PaymentRepo paymentRepo;

    //creates list, adds all the data in customer repo to the list and returns an output
    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();
        customerrepo.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer findCustomerById(Long custid) {

        return customerrepo.findById(custid)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + custid + " not found."));
    }

    @Override
    public List<Customer> findCustomersBySubstring(String substring) {
        List<Customer> list = customerrepo.findByCustnameContainingIgnoringCase(substring);
        return list;
    }

    @Override
    public List<OrderCounts> getOrderCount() {
        List<OrderCounts> list = customerrepo.findOrderCounts();
        return list;
    }

    //implement method to
    @Transactional
    @Override
    public Customer save(Customer customer){

        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0){

            customerrepo.findById(customer.getCustcode())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Customer " + customer.getCustcode() + " Not Found"));

            newCustomer.setCustcode(customer.getCustcode());
        }

        //assign customer value to newCustomer in a controlled way
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        //make sure newCustomer orders is empty
        //loop through orders and fill fields in a controlled way
        newCustomer.getOrders().clear();
        for(Order o: customer.getOrders()){

            //create a new order
            Order newOrder = new Order();

            //fill fields
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());

            //set order payments to clear
            //loop through provided orders
            newOrder.getPayments().clear();
            for(Payment p: newOrder.getPayments()){

                //verify orders exist
                Payment newPay = paymentRepo.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                //add order to the newOrder payment's array
                newOrder.getPayments().add(newPay);
            }

            //add new orders to new customer
            newCustomer.getOrders().add(newOrder);
        }

        //save new customer
        return customerrepo.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {

        //verify that the id is in the repo
        Customer currentCustomer = customerrepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found."));

        //check if field values have been provided and fill them if they exist
        if(customer.getCustname() != null){

            currentCustomer.setCustname(customer.getCustname());
        }
        if(customer.getCustname() != null){

            currentCustomer.setCustcity(customer.getCustcity());
        }
        if(customer.getCustname() != null){

            currentCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if(customer.getCustname() != null){

            currentCustomer.setCustcountry(customer.getCustcountry());
        }
        if(customer.getCustname() != null){

            currentCustomer.setGrade(customer.getGrade());
        }
        if(customer.getCustname() != null){

            currentCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if(customer.getCustname() != null){

            currentCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if(customer.getCustname() != null){

            currentCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if(customer.getCustname() != null){

            currentCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.getCustname() != null){

            currentCustomer.setPhone(customer.getPhone());
        }
        if(customer.getCustname() != null){

            currentCustomer.setAgent(customer.getAgent());
        }

        //loop through orders and fill fields in a controlled way
        if(customer.getOrders().size() > 0) {

            //make sure current customer orders is empty
            currentCustomer.getOrders().clear();

            //loop through customer orders
            for (Order o : customer.getOrders()) {

                //create a new order
                Order newOrder = new Order();

                //fill fields
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrderdescription(o.getOrderdescription());

                //set order payments to clear
                //loop through provided orders
                newOrder.getPayments().clear();
                for (Payment p : newOrder.getPayments()) {

                    //verify orders exist
                    Payment newPay = paymentRepo.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                    //add order to the newOrder payment's array
                    newOrder.getPayments()
                        .add(newPay);
                }

                //add new orders to current customer
                currentCustomer.getOrders().add(newOrder);
            }
        }
        //save new customer to repo
        return customerrepo.save(currentCustomer);
    }

    @Transactional
    @Override
    public void delete(long id) {

        customerrepo.findById(id).orElseThrow(() -> new EntityNotFoundException(
            "Restaurant " + id + " Not Found."
        ));
    }
}
