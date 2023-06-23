package com.store.store.Services;

import com.store.store.Model.Entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(int id);

    Optional<Customer> getCustomerByUsername(String username);
}

