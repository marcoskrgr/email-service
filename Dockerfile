# Estágio de construção
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
COPY src/ src

# Garantir que o mvnw tenha permissão de execução
RUN chmod +x mvnw

# Construir o projeto e empacotar o JAR
RUN ./mvnw clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:24-jdk
WORKDIR /app

# Instala curl para health
USER root
RUN apt-get update && apt-get install -y curl

# Criar usuário dedicado para rodar a aplicação
RUN groupadd -r appgroup && useradd -r -g appgroup appuser
USER appuser

# Copiar o JAR do estágio de construção
COPY --from=builder /app/target/email-service-0.0.1-SNAPSHOT.jar /app/email-service.jar

# Expor a porta da aplicação
EXPOSE 8080

# Health check para monitoramento
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
  CMD curl --fail http://localhost:8080/actuator/health || exit 1

# Variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=prod

# Comando de execução
ENTRYPOINT ["java", "-jar", "email-service.jar"]
