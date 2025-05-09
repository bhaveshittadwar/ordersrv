package com.example.ordersrv.web;

import com.example.ordersrv.service.OrderDto;
import com.example.ordersrv.domain.Order;
import com.example.ordersrv.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService svc;

    public OrderController(OrderService svc) {
        this.svc = svc;
    }

    /**
     * Create or update an order.
     */
    @PostMapping
    public ResponseEntity<Order> upsert(@RequestBody OrderDto dto) {
        Order o = svc.createOrUpdate(dto);
        return ResponseEntity.ok(o);
    }

    /**
     * Fetch an order by its externalId.
     * Returns 404 if not found.
     */
    @GetMapping("/{externalId}")
    public ResponseEntity<Order> getByExternalId(@PathVariable String externalId) {
        return svc.findByExternalId(externalId)
                  .map(ResponseEntity::ok)
                  .orElseThrow(() -> new ResponseStatusException(
                      HttpStatus.NOT_FOUND,
                      "Order with externalId=" + externalId + " not found"
                  ));
    }
}
