package com.example.orderservice.config;

import com.example.orderservice.model.Order;
import com.example.orderservice.respository.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataSetup {

    private final OrderRepository orderRepository;

    public DataSetup(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    public void setupData() {
        orderRepository.saveAll(Arrays.asList(
                Order.builder().id(1).orderNumber("0c70c0c2").postalCode("1000001").build(),
                Order.builder().id(2).orderNumber("7f8f9f15").postalCode("1100000").build(),
                Order.builder().id(3).orderNumber("394627b2").postalCode("2100001").build()));
    }
}
