// src/main/java/com/example/ordersrv/service/OrderDto.java
package com.example.ordersrv.service;

import java.math.BigDecimal;

public record OrderDto(String externalId,
                       BigDecimal amount,
                       String status) {

    public OrderDto(String externalId, double amount, String status) {
        this(externalId, BigDecimal.valueOf(amount), status);
    }

    // These getXxx() methods let Jackson/TestRestTemplate treat this like a POJO.
    public String getExternalId() { return externalId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus()   { return status; }
}
