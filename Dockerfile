# ====== STAGE 1: Build the application ======
FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests

FROM maven:3.8.5-openjdk-17-slim AS runtime

WORKDIR /app
COPY --from=builder /app /app

CMD ["mvn", "clean", "test"]