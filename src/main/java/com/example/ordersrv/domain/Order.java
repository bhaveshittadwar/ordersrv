// src/main/java/com/example/ordersrv/domain/Order.java
package com.example.ordersrv.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders",
       uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    public Order() {}

    public Order(String externalId, BigDecimal amount, String status) {
        this.externalId = externalId;
        this.amount     = amount;
        this.status     = status;
    }

    public Long getId() { return id; }
    public String getExternalId() { return externalId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }

    public void setExternalId(String externalId) { this.externalId = externalId; }
    public void setAmount(BigDecimal amount)     { this.amount     = amount; }
    public void setStatus(String status)         { this.status     = status; }
}