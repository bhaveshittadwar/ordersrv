// src/main/java/com/example/ordersrv/service/OrderService.java
package com.example.ordersrv.service;
import com.example.ordersrv.domain.Order;
import com.example.ordersrv.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
  private final OrderRepository repo;
  public OrderService(OrderRepository repo) { this.repo = repo; }

  public Order createOrUpdate(OrderDto dto) {
    return repo.findByExternalId(dto.externalId())
      .map(o -> {
        o.setAmount(dto.amount());
        o.setStatus(dto.status());
        return repo.save(o);
      })
      .orElseGet(() -> repo.save(new Order(dto.externalId(), dto.amount(), dto.status())));
  }
}