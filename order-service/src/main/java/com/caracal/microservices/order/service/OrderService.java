package com.caracal.microservices.order.service;

import com.caracal.microservices.order.client.InventoryClient;
import com.caracal.microservices.order.dto.OrderRequest;
import com.caracal.microservices.order.event.OrderPlacedEvent;
import com.caracal.microservices.order.model.Order;
import com.caracal.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isProductInStock) {
            //map order request to order object
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            //save order to OrderRepository
            orderRepository.save(order);

            //send message to kafka Topic
            // need order number and email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            log.info("Start - sending orderplacedevent {} to Kafka topic order-placed", orderPlacedEvent);
            kafkaTemplate.send("order-placed-event", orderPlacedEvent);
            log.info("End - sending orderplacedevent {} to Kafka topic order-placed", orderPlacedEvent);
        }else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }

    }


}

