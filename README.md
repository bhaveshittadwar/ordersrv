# Order Processor Service

A small Spring Boot microservice that lets you create and update **orders** over **REST** and **gRPC**, storing them in PostgreSQL.

---

## Table of Contents
1. [Project snapshot](#project-snapshot)  
2. [Prerequisites](#prerequisites)  
3. [Getting Started](#getting-started)  
4. [Running](#running)  
5. [API Usage](#api-usage)  
6. [Tests](#tests)  
7. [Project Structure](#project-structure)  
8. [Glossary](#glossary)

---

## Project snapshot
* **Language / Build** – Java 17 • Maven  
* **Frameworks** – Spring Boot 3, Spring Data JPA, Spring-gRPC  
* **I/O** – REST (JSON) + gRPC (Protobuf)  
* **DB** – PostgreSQL 14  
* **Tests** – JUnit 5 + Testcontainers (runs Postgres in Docker)

---

## Prerequisites
* Java 17 (JDK)  
* Maven 3.9+  
* Docker (only for integration tests)  
* PostgreSQL running locally or in Docker

---

## Getting Started

~~~bash
git clone git@github.com:<your-username>/ordersrv.git
cd ordersrv
mvn clean package
~~~

Configure DB in `src/main/resources/application.yml`:

~~~yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/orders
    username: user
    password: pass
~~~

---

## Running

~~~bash
mvn spring-boot:run
~~~

* REST API on **http://localhost:8080**  
* gRPC endpoint on the **same port**

---

## API Usage

### REST example

~~~bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"externalId":"ext100","amount":50.0,"status":"NEW"}'
~~~

### gRPC example (needs [`grpcurl`](https://github.com/fullstorydev/grpcurl))

~~~bash
grpcurl -plaintext \
  -d '{"externalId":"ext100","amount":50.0,"status":"NEW"}' \
  localhost:8080 ordersrv.OrderService/Upsert
~~~

---

## Tests

~~~bash
mvn clean test
~~~

* Unit tests run in memory.  
* Integration tests spin up Postgres via **Testcontainers**.

---

## Project Structure

~~~text
src/
 ├─ main/
 │   ├─ java/com/example/ordersrv/      ← Java source
 │   ├─ proto/                          ← Protobuf (.proto) files
 │   └─ resources/                      ← application.yml etc.
 └─ test/
     └─ java/com/example/ordersrv/      ← JUnit & Testcontainers
~~~

---

## Glossary

| Term | Two-line explanation |
|------|----------------------|
| **Spring Boot** | Framework that bundles an embedded server and auto-configures everything so you can run the app with one command. |
| **JPA** | Java Persistence API – maps Java objects to relational tables, letting you query with repository interfaces. |
| **REST** | HTTP-based API style that typically exchanges JSON using verbs like GET, POST, PUT, DELETE. |
| **gRPC** | High-performance binary Remote Procedure Call protocol (over HTTP/2) using Protobuf messages. |
| **Protobuf** | “Protocol Buffers”: small, language-neutral binary message format used by gRPC. |
| **Testcontainers** | Java library that launches real Docker containers during tests (here: a real Postgres). |
| **HikariCP** | Fast JDBC connection-pool library Spring uses to manage DB connections efficiently. |
