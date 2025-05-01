#!/bin/bash

# Atualizar código do repositório
git pull

# Configurar JVM para evitar avisos
export MAVEN_OPTS="--enable-native-access=ALL-UNNAMED"

# Construir as imagens e iniciar os containers com docker-compose
docker-compose up --build -d

# Limpar as imagens antigas do projeto que não estão em uso
docker image prune -f --filter "label=com.docker.compose.project=email-service"

# Limpar volumes não utilizados (opcional, dependendo do seu caso)
docker volume prune -f