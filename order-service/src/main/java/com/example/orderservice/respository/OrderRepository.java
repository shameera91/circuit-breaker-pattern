package com.example.orderservice.respository;

import com.example.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    Optional<Order> findByOrderNumber(String orderNumber);
}
