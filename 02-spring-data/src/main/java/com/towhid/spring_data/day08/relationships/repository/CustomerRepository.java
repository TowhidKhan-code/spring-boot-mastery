package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    // JPQL with JOIN
    // fetch join = load orders together with customer
    // avoids N+1 problem
    @Query("SELECT DISTINCT c FROM Customer c " +
            "LEFT JOIN FETCH c.orders " +
            "WHERE c.id = :id")
    Optional<Customer> findByIdWithOrders(Integer id);

}
