// src/test/java/com/example/ordersrv/OrderRepositoryTest.java
package com.example.ordersrv;

import com.example.ordersrv.domain.Order;
import com.example.ordersrv.repo.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
class OrderRepositoryTest {

  @Container
  static PostgreSQLContainer<?> pg = new PostgreSQLContainer<>("postgres:14");

  @DynamicPropertySource
  static void props(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", pg::getJdbcUrl);
    registry.add("spring.datasource.username", pg::getUsername);
    registry.add("spring.datasource.password", pg::getPassword);
  }

  @Autowired OrderRepository repo;

  @Test
  void idempotentSave() {
    var o1 = new Order("ext123", new BigDecimal("100"), "NEW");
    repo.saveAndFlush(o1);
    assertEquals(1, repo.count());

    var o2 = new Order("ext123", new BigDecimal("100"), "NEW");
    assertThrows(DataIntegrityViolationException.class,
                 () -> repo.saveAndFlush(o2));
  }
}