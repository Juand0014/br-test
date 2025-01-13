# 🚀 Client Management API - Quarkus + PostgreSQL

This is a **RESTful API** built with **Quarkus** that allows efficient **client management**, including:
- 📌 **Create, Read, Update, and Delete (CRUD) operations** for clients.
- 🌍 **Filter clients by country**.
- ✅ **Database migration using Flyway**.
- 📦 **Dockerized setup with PostgreSQL**.

## 📂 Project Structure

```
📦 client-test
├── 📂 src                     
│   ├── 📂 main
│   │   ├── 📂 java/org/acme   
│   │   │   ├── 📂 dto         # Request/Response DTOs
│   │   │   ├── 📂 entity      # JPA Entities
│   │   │   ├── 📂 exceptions  # Custom Exception Handlers
│   │   │   ├── 📂 repository  # Data Access Layer
│   │   │   ├── 📂 resource    # API Controllers (REST)
│   │   │   ├── 📂 services    # Business Logic
│   │   ├── 📂 resources
│   │   │   ├── 📂 db.migration  # Flyway Migrations
│   │   │   ├── 📄 application.properties  # Configuration
│   ├── 📂 test
│   │   ├── 📂 java/org/acme
│   │   │   ├── 📄 ClientResourceTest  # API Tests
│   │   │   ├── 📄 ClientServicesTest  # Service Tests
├── 📄 docker-compose.yml      # Docker Services
├── 📄 Dockerfile              # Quarkus Container Image
├── 📄 mvnw / mvnw.cmd         # Maven Wrapper
├── 📄 pom.xml                 # Dependencies & Build Config
└── 📄 README.md               # Documentation
```

---

## 🔧 **Installation & Setup**

### **1️⃣ Prerequisites**
Ensure you have the following installed:
- **Java 17+** → [Download](https://adoptopenjdk.net/)
- **Maven 3.8+** → [Download](https://maven.apache.org/)
- **Docker & Docker Compose** → [Install](https://docs.docker.com/engine/install/)

### **2️⃣ Clone the Repository**
```sh
git clone https://github.com/Juand0014/client-test.git
cd client-test
```

### **3️⃣ Configure Environment**
Modify `application.properties` if necessary:
```properties
# With Docker
quarkus.hibernate-orm.log.sql=true
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=admin
quarkus.datasource.jdbc.url=postgresql://postgres:5432/clientesdb

# Without Docker
quarkus.hibernate-orm.log.sql=true
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=admin
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/clientesdb

# Configuración de Hibernate ORM con Panache
quarkus.hibernate-orm.database.generation=update
quarkus.flyway.migrate-at-start=true
quarkus.flyway.locations=classpath:db/migration

# Rest Client
quarkus.rest-client.rest-countries-api.url=https://restcountries.com/v3.1
quarkus.rest-client.rest-countries-api.scope=ApplicationScoped

# Configurar el host para permitir conexiones externas
quarkus.http.host=0.0.0.0

# Docker
quarkus.container-image.build=true
quarkus.container-image.group=quarkus
quarkus.container-image.name=client-test-jvm
quarkus.container-image.tag=latest
quarkus.jib.ports=8080

# Swagger Config
quarkus.swagger-ui.always-include=true
quarkus.smallrye-openapi.path=/q/openapi

```

---

## 🚀 **Run the Application**

### **1️⃣ Run Locally (Without Docker)**
```sh
./mvnw clean package
./mvnw quarkus:dev
```
and run postgres container or configurate your local database

```sh
docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=clientesdb -p 5432:5432 -d postgres
```

- API will be available at **http://localhost:8080**

### **2️⃣ Run with Docker Compose**

Build the application using docker

```sh
# Build and package your project without running tests.
./mvnw clean package -DskipTests 
# Build the Docker image using the Quarkus plugin. 
./mvnw quarkus:image-build  
# Verify that the image is in Docker with
docker images
# Lifting the containers with
docker compose up --build -d
```
- API: **http://localhost:8080**
- PostgreSQL: **localhost:5432**
- Swagger UI: **http://localhost:8080/q/swagger-ui**



---

## 📡 **REST API Endpoints**
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/clients` | Create a new client |
| `GET` | `/clients` | Get all clients |
| `GET` | `/clients/{id}` | Get a client by ID |
| `GET` | `/clients/country/{countryCode}` | Get clients by country |
| `PUT` | `/clients/{id}` | Update client (email, phone, address, country) |
| `DELETE` | `/clients/{id}` | Delete a client |

---

## ✅ **Testing**

### **1️⃣ Run Unit Tests**
```sh
./mvnw test
```

### **2️⃣ Manual Testing with Swagger UI**
- **Swagger UI** → [http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)
- **API Health Check** → [http://localhost:8080/q/health](http://localhost:8080/q/health)

---

# 📌 Quarkus Client Test Project

Este proyecto es un servicio **RESTful** basado en **Quarkus** que gestiona clientes y permite realizar operaciones **CRUD** con persistencia en **PostgreSQL**.  
Incluye documentación en **Swagger**, validación de datos y soporte para migraciones con **Flyway**.

---

## 📦 **Dependencias del Proyecto**
A continuación, se listan las dependencias utilizadas junto con su documentación oficial.

| Dependencia | Descripción | Documentación |
|------------|-------------|---------------|
| **Quarkus REST** | Implementación JAX-RS para servicios REST. | [Docs](https://quarkus.io/guides/rest-json) |
| **Quarkus REST Jackson** | Soporte para serialización JSON con Jackson. | [Docs](https://quarkus.io/guides/rest-json#jackson) |
| **Quarkus Hibernate ORM Panache** | Simplifica el uso de Hibernate ORM. | [Docs](https://quarkus.io/guides/hibernate-orm-panache) |
| **Quarkus Flyway** | Manejo de migraciones de base de datos. | [Docs](https://quarkus.io/guides/flyway) |
| **Quarkus JDBC PostgreSQL** | Conector JDBC para PostgreSQL. | [Docs](https://quarkus.io/guides/datasource) |
| **Quarkus REST Client** | Cliente REST declarativo. | [Docs](https://quarkus.io/guides/rest-client) |
| **Lombok** | Eliminación de código repetitivo en Java. | [Docs](https://projectlombok.org/) |
| **Quarkus Hibernate Validator** | Validación de datos con Hibernate Validator. | [Docs](https://quarkus.io/guides/validation) |
| **Quarkus SmallRye OpenAPI** | Generación automática de documentación OpenAPI. | [Docs](https://quarkus.io/guides/openapi-swaggerui) |
| **Quarkus Swagger UI** | Interfaz gráfica para probar los endpoints. | [Docs](https://quarkus.io/guides/openapi-swaggerui) |

---

## 🧪 **Dependencias de Pruebas**
| Dependencia | Descripción | Documentación |
|------------|-------------|---------------|
| **Quarkus JUnit 5** | Pruebas con JUnit 5 en Quarkus. | [Docs](https://quarkus.io/guides/getting-started-testing) |
| **Mockito Core** | Framework para simulación de objetos en pruebas. | [Docs](https://site.mockito.org/) |
| **Mockito JUnit Jupiter** | Integración de Mockito con JUnit 5. | [Docs](https://javadoc.io/doc/org.mockito/mockito-junit-jupiter/latest/index.html) |
| **Rest Assured** | Testing de API REST en Java. | [Docs](https://rest-assured.io/) |

---

---

## 📌 **Troubleshooting**

### ❌ PostgreSQL Connection Issues?
- Check logs: `docker logs postgres`
- Ensure DB is running: `docker ps`
- Manually restart: `docker restart postgres`

### ❌ Swagger Not Found?
- Ensure Quarkus OpenAPI is installed:
  ```xml
  <dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-smallrye-openapi</artifactId>
  </dependency>
  ```
- Restart Quarkus: `./mvnw quarkus:dev`
