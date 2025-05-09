package com.example.ordersrv.grpc;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ordersrv.OrderReq;
import ordersrv.OrderResp;
import ordersrv.OrderServiceGrpc;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GrpcServiceIT {

    @LocalServerPort
    private int port;

    @Test
    void upsertGrpcThenGet() {
        ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", port)
            .usePlaintext()
            .build();

        var stub = OrderServiceGrpc.newBlockingStub(channel);
        var req = OrderReq.newBuilder()
            .setExternalId("extGRPCIT")
            .setAmount(99.9)
            .setStatus("NEW")
            .build();

        OrderResp resp = stub.upsert(req);
        assertThat(resp.getExternalId()).isEqualTo("extGRPCIT");

        channel.shutdownNow();
    }
}
