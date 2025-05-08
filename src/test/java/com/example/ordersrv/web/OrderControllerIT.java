package com.example.ordersrv.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = "grpc.security.enabled=false"  // ensure LogNet gRPC security is turned off
)
@AutoConfigureMockMvc(addFilters = false)      // disable Spring Security filters for the test
class OrderControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void upsertCreatesAndReturnsOrder() throws Exception {
        // adjust field names to exactly match your OrderController's DTO
        String newOrderJson = """
            {
                "product": "Widget",
                "quantity": 5,
                "price": 9.99
            }
            """;

        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newOrderJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.product").value("Widget"))
            .andExpect(jsonPath("$.quantity").value(5))
            .andExpect(jsonPath("$.price").value(9.99));
    }
}
