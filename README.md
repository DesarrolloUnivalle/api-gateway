# API Gateway

Este es el API Gateway del sistema de gestión de órdenes, construido con Spring Cloud Gateway.

## Características

- **Puerto**: 9000
- **Framework**: Spring Boot 3.4.4 + Spring Cloud Gateway
- **Java**: 21
- **Registro de servicios**: Eureka Client
- **Monitoreo**: Actuator + Prometheus + Zipkin
- **Logs**: Configuración detallada para debugging

## Rutas configuradas

- `/usuarios/**` y `/auth/**` → usuarios-service
- `/api/ordenes/**` → ordenes-service  
- `/api/productos/**` y `/categorias/**` → productos-service
- `/api/entregas/**` → entregas-service

## Endpoints de monitoreo

- Health: `http://localhost:9000/actuator/health`
- Prometheus: `http://localhost:9000/actuator/prometheus`
- Info: `http://localhost:9000/actuator/info`

## Despliegue con GitHub Actions

El proyecto incluye un workflow de GitHub Actions que:

1. **Build y test**: Compila el proyecto y ejecuta tests
2. **Docker**: Construye y sube la imagen a Docker Hub
3. **Kubernetes**: Despliega en Minikube para testing
4. **Verificación**: Valida que el servicio esté funcionando
5. **Logs detallados**: Proporciona información completa para debugging

### Secrets requeridos

- `DOCKER_USERNAME`: Usuario de Docker Hub
- `DOCKER_PASSWORD`: Contraseña de Docker Hub

### Trigger del workflow

El workflow se ejecuta automáticamente en:
- Push a `main`
- Push a `test-workflow`

## Logs

El API Gateway está configurado con logs detallados para facilitar el debugging:

- Logs de rutas de gateway
- Logs de filtros
- Logs de WebFlux
- Logs de Reactor Netty

## Configuración de producción

El archivo `application-prod.yml` contiene la configuración optimizada para producción con:
- URLs de servicios actualizadas para Kubernetes
- Logs de debug habilitados
- Configuración de health checks detallada
