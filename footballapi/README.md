# ⚽ Football API – Gestion de l'équipe de Nice (Ligue 1)

Ce projet est une API REST développée avec **Spring Boot** permettant de gérer les équipes de football, notamment l'équipe de **Nice** en Ligue 1. L’API permet :

- D’ajouter une équipe avec ou sans joueurs
- De lister les équipes avec pagination et tri
- De persister les données en base H2
- De tester le projet avec des tests unitaires et d’intégration

---

## 🚀 Objectif

Le directeur sportif du club souhaite une interface API permettant de :
- Gérer son budget
- Répertorier les joueurs
- Préparer le marché des transferts

---

## 🛠️ Technologies utilisées

| Outil             | Version        | Utilisation                           |
|------------------|----------------|----------------------------------------|
| Java             | 21             | Langage principal                      |
| Spring Boot      | 3.5.0          | Framework principal (Web, JPA, Test)   |
| Hibernate        |                | ORM pour la persistance                |
| H2 Database      | intégrée       | Base de données embarquée              |
| MapStruct        | 1.5.5.Final    | Mapping entités ↔ DTOs                 |
| Lombok           | 1.18.30        | Génération automatique du code         |
| JUnit 5          |                | Framework de tests                     |
| JGiven           | 1.3.1          | Tests BDD (Given / When / Then)        |

---

## 📂 Structure du projet

```
src
├── main
│   ├── java/com/matawan/footballapi
│   │   ├── controller       → Contrôleur REST (EquipeController)
│   │   ├── dto              → Objets EquipeDto, JoueurDto
│   │   ├── entity           → Entités JPA (Equipe, Joueur)
│   │   ├── mapper           → MapStruct (EquipeMapper)
│   │   ├── repository       → DAO Spring Data (EquipeRepository, JoueurRepository)
│   │   ├── service          → Logique métier (EquipeService)
│   │   ├── exception        → Gestion d’erreurs globales
│   │   └── FootballapiApplication.java → Point d'entrée Spring Boot
│
├── test
│   ├── java/com/matawan/footballapi
│   │   ├── unit             → Tests unitaires (EquipeServiceTest, Mapper)
│   │   ├── integration      → Tests intégration (EquipeIntegrationTest)
│   │   └── jgiven           → Given / When / Then (Mapper)
```

---

## ⚙️ Installation

### 1. Prérequis
- Java 21 installé
- Maven 3.x
- (facultatif) IDE comme IntelliJ ou Eclipse

### 2. Clonage et compilation
```bash
git clone <votre-lien-git>
cd footballapi
mvn clean install
```

### 3. Lancement de l'application
```bash
mvn spring-boot:run
```

L’API sera accessible à : `http://localhost:8080`

### 4. Console H2 (Base de données)
- URL : `http://localhost:8080/h2-console`
- JDBC URL : `jdbc:h2:file:./data/footballdb`
- User : `sa`
- Password : *(laisser vide)*

---

## 📌 Fonctionnalités principales

### ➕ Ajouter une équipe
```http
POST /api/equipes
Content-Type: application/json

{
  "name": "Paris Saint-Germain",
  "acronym": "PSG",
  "budget": 5000000,
  "joueurs": [
    { "name": "Mbappé", "position": "Attaquant" }
  ]
}
```

### 📄 Lister les équipes (pagination + tri)
```http
GET /api/equipes?page=0&size=5&sort=name,asc
```
- **Tri possible sur** : `name`, `acronym`, `budget`

---

## ✅ Tests

- `EquipeServiceTest` → tests unitaires sur la couche service
- `EquipeMapperTest` → test BDD avec JGiven (Given/When/Then)
- `EquipeIntegrationTest` → test d’intégration de l’API REST

Commandes :
```bash
mvn test
```

---

## 💡 Choix techniques

- **Base H2** : base embarquée simple à configurer, idéale pour les tests
- **MapStruct** : facilite les conversions entre entités et DTOs
- **Lombok** : réduit la verbosité des entités
- **JGiven** : permet des scénarios de test clairs et lisibles
- **Validation** : annotations `@Valid`, `@NotNull`, `@Size`, etc.
- **Gestion d'erreur centralisée** : via `@ControllerAdvice`

---

## 🕒 Temps estimé passé

Environ **10 à 12 heures** :
- 2h modélisation + configuration
- 3h développement des endpoints
- 2h tests unitaires et intégration
- 2h nettoyage et gestion d’erreur
- 2h documentation et finalisation

---

## 📬 Livraison

Merci de retourner ce projet à :

```
contact@matawan-services.com
```

---

## 👤 Auteur

Antonio Gerges — Étudiant développeur Java / Spring Boot
