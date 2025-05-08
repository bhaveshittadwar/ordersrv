// src/test/java/com/example/ordersrv/service/OrderServiceTest.java
package com.example.ordersrv.service;
import com.example.ordersrv.domain.Order;
import com.example.ordersrv.repo.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
  @Mock OrderRepository repo;
  @InjectMocks OrderService service;

  @Test void createNew() {
    var dto = new OrderDto("ext1", BigDecimal.ONE, "NEW");
    when(repo.findByExternalId("ext1")).thenReturn(Optional.empty());
    when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
    var o = service.createOrUpdate(dto);
    assertEquals("ext1", o.getExternalId());
    verify(repo).save(any(Order.class));
  }

  @Test void updateExisting() {
    var existing = new Order("ext2", BigDecimal.TEN, "OLD");
    when(repo.findByExternalId("ext2")).thenReturn(Optional.of(existing));
    when(repo.save(existing)).thenReturn(existing);
    var dto = new OrderDto("ext2", BigDecimal.valueOf(20), "NEW");
    var o = service.createOrUpdate(dto);
    assertEquals(BigDecimal.valueOf(20), o.getAmount());
    assertEquals("NEW", o.getStatus());
    verify(repo, never()).save(argThat(o1 -> o1.getExternalId().equals("ext2") && !o1.equals(existing)));
  }
}