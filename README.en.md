# ⚽ Football API – Manage the Nice Football Team (Ligue 1)

[![Java](https://img.shields.io/badge/Java-21-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Dockerized](https://img.shields.io/badge/Docker-ready-blue)](https://www.docker.com/)


🇫🇷 [Version française disponible ici](README.md)

This project is a REST API developed with **Spring Boot** to manage football teams, especially the **Nice** team in Ligue 1. The API allows:

- Adding a team with or without players
- Listing teams with pagination, search, and sorting
- Persisting data using the H2 in-memory database
- Server-side sorting on `name`, `acronym`, and `budget`
- API testing with Postman using routes like `POST /api/equipes` and `GET /api/equipes`
- Using DTOs to expose only the necessary data externally
- Leveraging Lombok and MapStruct to reduce boilerplate and improve readability
- Designing for scalability (e.g., early creation of `JoueurRepository`)
- Unit and integration testing

---

## 🚀 Objective

The club's sporting director wants an API interface to:
- Manage the team budget
- List and organize players
- Prepare for the upcoming transfer window

---

## 🛠️ Technologies Used

| Tool             | Version        | Purpose                               |
|------------------|----------------|----------------------------------------|
| Java             | 21             | Main language                          |
| Spring Boot      | 3.5.0          | Web framework (Web, JPA, Test)         |
| Hibernate        |                | ORM for persistence                    |
| H2 Database      | Embedded       | In-memory test database                |
| MapStruct        | 1.5.5.Final    | Entity ↔ DTO mapping                 |
| Lombok           | 1.18.30        | Automatic getter/setter generation     |
| JUnit 5          |                | Unit testing                           |
| JGiven           | 1.3.1          | BDD testing (Given/When/Then)          |

---

## 📂 Project Structure

```
src
├── main
│   └── java/com/matawan/footballapi
│       ├── controller       → REST controller (EquipeController)
│       ├── dto              → DTOs: EquipeDto, JoueurDto
│       ├── entity           → Entities: Equipe, Joueur
│       ├── mapper           → MapStruct (EquipeMapper)
│       ├── repository       → Spring Data JPA (EquipeRepository, JoueurRepository)
│       ├── service          → Business logic (EquipeService)
│       └── FootballapiApplication.java → Main Spring Boot class
│
└── test
    └── java/com/matawan/footballapi
        ├── integration       → Integration tests (EquipeIntegrationTest)
        ├── mapper            → Unit tests (EquipeMapperTest) + JGiven (G/W/T)
        ├── service           → Unit tests (EquipeServiceTest)
        └── FootballApiApplicationTests
📦 docker-compose.yml      → Container configuration file (build + run)  
🐳 Dockerfile              → Docker image for the application

```


## 🐳 Containerization with Docker

The project is fully **containerized** in order to:

- Avoid any complex local setup
- Make the project reproducible regardless of the operating system
- Allow anyone to launch the API with a single command

---

## 📁 Docker Structure

- `Dockerfile`: builds a minimal Java image (based on Alpine)
- `docker-compose.yml`: defines the `app` service, mounts a **Docker volume** to persist H2 data even after shutdown

---

### 🔐 Data Persistence

H2 database files are stored inside `/app/data`, which is mounted to a **Docker volume (`h2-data`)**.  
👉 This ensures that your data is not lost even after running `docker-compose down`.

---

## ▶️ Installation and Execution with Docker

### 1. ✅ Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop) installed
- Git (or download the project as a .zip)

```bash
git clone https://github.com/Buzzz96/football-api.git
cd football-api

```
2. 🚀 Run the project
   ⚠️ Before running docker-compose up, make sure the .jar file exists.
   Run one of the following commands:

```bash
./mvnw clean package
```

# OR if Maven is already installed:

```bash
mvn clean package
```
⚠️ Ensure Docker Desktop is running before executing the command below.
Otherwise, the build may fail or hang because the Docker engine will be inaccessible.

```bash
docker-compose up --build

```

---

## ⚙️ Installation without docker

### 1. Prerequisites
- Java 21 installed
- Maven 3.x
- (Optional) IntelliJ IDEA or Eclipse

### 2. Clone the Project
```bash
git clone https://github.com/Buzzz96/football-api.git
cd football-api
```

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```
The API will be accessible at: `http://localhost:8080`

### 5. H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/footballdb`
- User: `sa`
- Password: *(leave blank)*

---

## 📌 Core Features

### 📬 Using the API with Postman and Browser

#### ➕ Add a Team with Postman
1. Method: **POST**
2. URL: `http://localhost:8080/equipes`
3. Tab: **Body** > **raw** > **JSON**
4. Payload:
```json
{
  "name": "Paris Saint-Germain",
  "acronym": "PSG",
  "budget": 5000000,
  "joueurs": [
    { "name": "Mbappé", "position": "Attaquant" }
  ]
}
```
5. Click **Send**

**Expected response:**
```json
{
  "name": "Paris Saint-Germain",
  "acronym": "PSG",
  "budget": 5000000.0,
  "joueurs": [
    {
      "name": "Mbappé",
      "position": "Attaquant"
    }
  ]
}
```

#### ❌ Delete a Team
1. Method: **DELETE**
2. URL: `http://localhost:8080/equipes/{id}` (e.g. `/equipes/5`)
3. Body: Empty
4. Click **Send**

**Expected response:** `204 No Content`

> If JPA mapping is correctly set (`cascade = ALL`, `orphanRemoval = true`), players related to the deleted team are also removed.

#### 📄 List Teams via Browser
- All teams: `http://localhost:8080/equipes`
- With pagination and sorting: `http://localhost:8080/equipes?page=0&size=5&sort=name,asc`
- Search by name: `http://localhost:8080/equipes/search?name=Paris`

---

### 📌 Route Summary

```
POST    /equipes                         → Add a team with players
GET     /equipes                         → List all teams
GET     /equipes?page=0&size=5&sort=... → Paginated & sorted listing
GET     /equipes/search?name=...        → Search teams by name
DELETE  /equipes/{id}                   → Delete a team by ID
```

---

## 💡 Technical Choices

- **H2 Database**: Lightweight, easy for testing and development
- **MapStruct**: For efficient and type-safe mapping between entities and DTOs
- **Lombok**: Reduces boilerplate code for entities
- **JGiven**: Enables clean and descriptive BDD-style tests
- **Validation**: Uses annotations like `@Valid`, `@NotNull`, `@Size`
- **Centralized error handling** with `@ControllerAdvice`

---

## ⏱️ Estimated Time Spent

Total estimated time: **16 to 20 hours**, including:
- 🧠 Designing the data model (entities `Equipe` and `Joueur`)
- 🛠️ Developing the REST API (GET with pagination, POST with validation)
- ✅ Validation with `@Valid`, `@NotBlank`, `@NotNull`
- 🔬 Writing unit and integration tests (MockMvc, JGiven)
- 🧪 Manual API testing via Postman
- 📄 Writing documentation and installation guide

> 💡 Developed during a very intensive academic period (9am–7pm daily classes), this project reflects personal commitment, consistency, and quality-focused development during evenings and weekends.

---

## 👤 Author
Antonio Gerges — Java / Spring Boot Developer
