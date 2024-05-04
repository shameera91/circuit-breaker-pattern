package com.example.orderservice.service.impl;

import com.example.orderservice.dto.AddressDTO;
import com.example.orderservice.model.Failure;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Type;
import com.example.orderservice.respository.OrderRepository;
import com.example.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private static final String SERVICE_NAME = "order-service";
    private static final String ADDRESS_SERVICE_URL = "http://localhost:9090/addresses/";

    public OrderServiceImpl(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public Type getOrderByPostCode(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order Not Found: " + orderNumber));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AddressDTO> entity = new HttpEntity<>(null, headers);
        ResponseEntity<AddressDTO> response = restTemplate.exchange(
                (ADDRESS_SERVICE_URL + order.getPostalCode()), HttpMethod.GET, entity,
                AddressDTO.class);
        AddressDTO addressDTO = response.getBody();
        if (addressDTO != null) {
            order.setShippingState(addressDTO.getState());
            order.setShippingCity(addressDTO.getCity());
        }
        return order;
    }

    private Type fallbackMethod(Exception e) {
        log.error("Error occurred. inside fallback");
        return new Failure("Address service is not responding properly");
    }
}
