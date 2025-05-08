// src/main/java/com/example/ordersrv/web/OrderController.java
package com.example.ordersrv.web;

import com.example.ordersrv.service.OrderDto;
import com.example.ordersrv.domain.Order;
import com.example.ordersrv.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService svc;
    public OrderController(OrderService svc) { this.svc = svc; }

    @PostMapping
    public ResponseEntity<Order> upsert(@RequestBody OrderDto dto) {
        Order o = svc.createOrUpdate(dto);
        return ResponseEntity.ok(o);
    }
}
