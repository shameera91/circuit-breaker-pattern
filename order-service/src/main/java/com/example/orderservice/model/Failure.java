package com.example.orderservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Failure implements Type{
    private final String msg;
}
