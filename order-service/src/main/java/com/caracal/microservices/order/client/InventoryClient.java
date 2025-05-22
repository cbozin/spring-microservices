package com.caracal.microservices.order.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    // name of breaker should match instance name in properties file
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);

    //if not able to access service this method is called
    default boolean fallBackMethod(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for sku-code {}, failure reason: {}", skuCode, throwable.getMessage());
        return false;
    }
}
