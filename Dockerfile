# -------- Stage 1: build --------
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app

# copy pom + download deps
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy the source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# -------- Stage 2: runtime --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy the built jar from builder
COPY --from=builder /app/target/ordersrv-0.0.1-SNAPSHOT.jar ./ordersrv.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/ordersrv.jar"]
