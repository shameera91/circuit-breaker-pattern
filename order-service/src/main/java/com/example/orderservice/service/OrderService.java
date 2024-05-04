package com.example.orderservice.service;

import com.example.orderservice.model.Type;

public interface OrderService {
    Type getOrderByPostCode(String orderNumber);
}
