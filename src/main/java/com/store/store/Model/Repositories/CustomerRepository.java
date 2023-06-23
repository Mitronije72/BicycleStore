package com.store.store.Model.Repositories;

import com.store.store.Model.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

   Optional<Customer> findByUsername(String username);
}
