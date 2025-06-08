# Etapa 1: Build
FROM openjdk:21-jdk-slim AS builder

WORKDIR /app

COPY gradlew gradlew.bat ./
COPY gradle/ gradle/
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x gradlew

COPY src/ src/

RUN ./gradlew clean bootJar -x test --no-daemon

# Etapa 2: Runtime
FROM openjdk:21-jdk-slim

WORKDIR /app

# Instalar curl para health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

# Variables de entorno optimizadas para Railway
ENV JAVA_OPTS="-Xmx400m -Xms200m -XX:+UseContainerSupport"

# Health check interno del contenedor
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/inktracks/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]