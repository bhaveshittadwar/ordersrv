// src/main/java/com/example/ordersrv/grpc/OrderGrpcService.java
package com.example.ordersrv.grpc;

import ordersrv.OrderReq;
import ordersrv.OrderResp;
import ordersrv.OrderServiceGrpc.OrderServiceImplBase;

import com.example.ordersrv.service.OrderDto;
import com.example.ordersrv.service.OrderService;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import java.math.BigDecimal;

@GrpcService
public class OrderGrpcService extends OrderServiceImplBase {

    private final OrderService svc;

    public OrderGrpcService(OrderService svc) {
        this.svc = svc;
    }

    @Override
    public void upsert(OrderReq req, StreamObserver<OrderResp> obs) {

        var saved = svc.createOrUpdate(new OrderDto(
            req.getExternalId(),
            BigDecimal.valueOf(req.getAmount()),
            req.getStatus()
        ));

        obs.onNext(OrderResp.newBuilder()
                .setExternalId(saved.getExternalId())
                .setAmount(saved.getAmount().doubleValue())
                .setStatus(saved.getStatus())
                .build());
        obs.onCompleted();
    }
}
