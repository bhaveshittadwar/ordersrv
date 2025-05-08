package com.example.ordersrv;

import org.springframework.boot.SpringApplication;

public class TestOrdersrvApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrdersrvApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
