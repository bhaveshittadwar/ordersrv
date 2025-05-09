package com.example.ordersrv.service;

import com.example.ordersrv.domain.Order;
import com.example.ordersrv.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    /**
     * Create or update an order.
     */
    public Order createOrUpdate(OrderDto dto) {
        return repo.findByExternalId(dto.externalId())
                   .map(o -> {
                       o.setAmount(dto.amount());
                       o.setStatus(dto.status());
                       return repo.save(o);
                   })
                   .orElseGet(() -> repo.save(new Order(
                       dto.externalId(),
                       dto.amount(),
                       dto.status()
                   )));
    }

    /**
     * Look up an order by its externalId.
     * Returns empty if none found.
     */
    public Optional<Order> findByExternalId(String externalId) {
        return repo.findByExternalId(externalId);
    }
}
