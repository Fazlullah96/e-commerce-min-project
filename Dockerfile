FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build

COPY pom.xml .
COPY eureka-server/pom.xml eureka-server/
COPY api-gateway/pom.xml api-gateway/
COPY user-service/pom.xml user-service/
COPY product-service/pom.xml product-service/
COPY order-service/pom.xml order-service/

RUN mvn dependency:go-offline -B

COPY eureka-server/src eureka-server/src
COPY api-gateway/src api-gateway/src
COPY user-service/src user-service/src
COPY product-service/src product-service/src
COPY order-service/src order-service/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
ARG SERVICE_NAME
WORKDIR /app
COPY --from=builder /build/${SERVICE_NAME}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]