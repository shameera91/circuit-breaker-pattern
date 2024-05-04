package com.example.orderservice.controller;

import com.example.orderservice.model.Type;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Type getByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getOrderByPostCode(orderNumber);
    }
}
