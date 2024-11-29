package com.tech.microservices.product.dto;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


public record ProductResponse (String id, String name, String description, BigDecimal price){

}
