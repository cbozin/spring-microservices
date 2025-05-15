package com.caracal.microservices.order.dto;

import com.caracal.microservices.order.model.Order;

import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber, String skuCode, BigDecimal price, Integer quantity) {
}
