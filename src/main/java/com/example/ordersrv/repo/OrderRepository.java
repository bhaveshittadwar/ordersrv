package com.example.ordersrv.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordersrv.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  Optional<Order> findByExternalId(String externalId);
}