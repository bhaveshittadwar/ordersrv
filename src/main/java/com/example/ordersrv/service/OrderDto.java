// src/main/java/com/example/ordersrv/service/OrderDto.java
package com.example.ordersrv.service;

import java.math.BigDecimal;

public record OrderDto(String externalId,
                       BigDecimal amount,
                       String status) {

    // Add these so your tests (and any legacy code) can still call getXxx()
    public String getExternalId() { return externalId; }
    public BigDecimal getAmount()   { return amount;     }
    public String getStatus()       { return status;     }
}
