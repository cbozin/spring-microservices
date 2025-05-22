package com.caracal.microservices.order.dto;

import com.caracal.microservices.order.model.Order;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price,
                           Integer quantity, UserDetails userDetails)
{
    public record UserDetails(String email, String firstName, String lastName){};
}
