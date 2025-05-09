package com.example.ordersrv.web;

import java.math.BigDecimal;
import com.example.ordersrv.service.OrderDto;
import com.example.ordersrv.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void upsertThenGet() {
        var dto = new OrderDto("extIT", BigDecimal.valueOf(42.0), "NEW");
        ResponseEntity<Order> post = rest.postForEntity("/orders", dto, Order.class);
        assertThat(post.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Order> get = rest.getForEntity("/orders/extIT", Order.class);
        assertThat(get.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(get.getBody().getExternalId()).isEqualTo("extIT");
    }
}
