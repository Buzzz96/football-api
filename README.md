# ⚽ Football API – Gestion de l'équipe de Nice (Ligue 1)

en [English version available here](README.en.md)


Ce projet est une API REST développée avec **Spring Boot** permettant de gérer les équipes de football, notamment l'équipe de **Nice** en Ligue 1. L’API permet :

- D’ajouter une équipe avec ou sans joueurs
- De lister les équipes avec pagination , recherche et tri
- De persister les données en base H2
- De permettre le tri côté serveur sur les champs `nom`, `acronyme` et `budget`
- De tester l’API avec Postman via les routes `POST /api/equipes` et `GET /api/equipes`
- D’utiliser des DTOs pour exposer uniquement les données nécessaires à l’extérieur
- D’utiliser Lombok et MapStruct pour réduire le code boilerplate et améliorer la lisibilité
- De préparer le projet à l’évolutivité (ex : création anticipée du `JoueurRepository`)
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
| Conteneurisation     | Docker + Docker Compose                  | Facile à exécuter sur n’importe quelle machine |


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
│   │   └── FootballapiApplication.java → Point d'entrée Spring Boot
│
 test
└──  java/com/matawan/footballapi
    ├──  integration       → Tests d’intégration (EquipeIntegrationTest)
    ├──  mapper            → Tests unitaires (EquipeMapperTest) + JGiven (Given/When/Then)
    ├──  service           → Tests unitaires (EquipeServiceTest)
    └──  FootballApiApplicationTests
```

## 🐳 Conteneurisation avec Docker

Le projet est entièrement **conteneurisé** pour :

- Éviter toute installation locale complexe
- Rendre le projet **reproductible**, peu importe l’OS
- Permettre à n’importe qui de lancer l’API en une commande

---

## 📁 Structure Docker

- `Dockerfile` : construit une image Java minimaliste (alpine)
- `docker-compose.yml` : définit le service `app`, monte un **volume Docker** pour conserver les données H2 même après arrêt

### 🔐 Persistance des données

Les données H2 sont enregistrées dans `/app/data`, monté vers un **volume Docker (`h2-data`)**.  
👉 Cela garantit que les données ne sont pas perdues même après `docker-compose down`.

---

## ▶️ Installation et exécution avec Docker

### 1. ✅ Prérequis

- [Docker Desktop](https://www.docker.com/products/docker-desktop) installé
- Git (ou téléchargement du projet .zip)

```bash
git clone https://github.com/Buzzz96/football-api.git
cd football-api
```


---

### 2. 🚀 Lancer le projet

```bash
docker-compose up --build
```

---

## ⚙️ Installation sans Docker

### 1. Prérequis
- Java 21 installé
- Maven 3.x
- (facultatif) IDE comme IntelliJ ou Eclipse

### 2. Clonage du projet
```bash
git clone https://github.com/Buzzz96/football-api.git
cd football-api
```


### 3. Compilation du projet
```bash
mvn clean install
```

### 4. Lancement de l'application
```bash
mvn spring-boot:run
```

L’API sera accessible à l'adresse suivante : `http://localhost:8080`

### 5. Console H2 (interface base de données)
- URL : `http://localhost:8080/h2-console`
- JDBC URL : `jdbc:h2:file:./data/footballdb`
- User : `sa`
- Password : *(laisser vide)*

---

## 📌 Fonctionnalités principales
## 📬 Utilisation de l’API via Postman et navigateur

### ➕ Ajouter une équipe via Postman

Pour tester l’ajout d’une équipe avec Postman :

1. Méthode : **POST**
2. URL : `http://localhost:8080/equipes`
3. Onglet : **Body** > cocher **raw** > choisir **JSON**
4. Coller ce contenu :

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

5. Cliquer sur **Send**

#### ✅ Résultat attendu

- **Status :** 201 Created (vert)
- **Réponse (en bas de Postman)** :

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
### ❌ Supprimer une équipe via Postman

Pour supprimer une équipe par son identifiant :

1. **Méthode :** `DELETE`
2. **URL :** `http://localhost:8080/equipes/{id}`  
   Exemple : `http://localhost:8080/equipes/5`
3. **Onglet Body :** laisser vide
4. Cliquer sur **Send**

#### ✅ Résultat attendu

- **Status :** `204 No Content`
- L’équipe est supprimée définitivement de la base de données.  
  Si le mapping JPA est bien configuré (`cascade = ALL` + `orphanRemoval = true`), les joueurs liés sont également supprimés.

---

### 📄 Lister les équipes via un navigateur

Vous pouvez utiliser n’importe quel navigateur pour tester les routes **GET** :

- Toutes les équipes :  
  `http://localhost:8080/equipes`

- Pagination + tri par nom (ascendant) :  
  `http://localhost:8080/equipes?page=0&size=5&sort=nom,asc`

- Rechercher une équipe par nom (ex: Paris) :  
  `http://localhost:8080/equipes/search?name=Paris`

Les résultats s'affichent au format **JSON** dans le navigateur.

---

### 📌 Récapitulatif des routes utiles

```
Méthode : POST
URL     : http://localhost:8080/equipes
Usage   : Ajouter une équipe avec joueurs

Méthode : GET
URL     : http://localhost:8080/equipes
Usage   : Lister toutes les équipes

Méthode : GET
URL     : http://localhost:8080/equipes?page=0&size=5
Usage   : Pagination + tri

Méthode : GET
URL     : http://localhost:8080/equipes/search?name=Paris
Usage   : Rechercher une équipe par son nom
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
## ⏱️ Temps estimé passé sur le projet

Le développement de cette API a été réalisé sur une durée estimée de **16 à 20 heures**, réparties entre plusieurs étapes clés :

- 🧠 **Conception du modèle de données** (entités `Equipe` et `Joueur`)
- 🛠️ **Développement de l’API REST** avec Spring Boot (GET paginé, POST avec validation)
- ✅ **Mise en place des validations** (`@Valid`, `@NotBlank`, `@NotNull`) côté DTO et entités
- 🔬 **Écriture des tests unitaires** avec JGiven (tests de mapping) et **tests d’intégration** avec MockMvc
- 🧪 **Tests manuels** via Postman pour valider les cas d’usage réels
- 📄 **Rédaction de la documentation** et du guide d’installation

> 💡 Ce projet a été mené **en parallèle de la période de soutenances académiques**, avec un emploi du temps très chargé (cours de 9h à 19h du lundi au vendredi).  
> Malgré ce contexte, le projet a été traité avec rigueur, passion et investissement, notamment en soirée et le week-end.  
> L’objectif a toujours été de livrer une solution propre, stable et bien structurée.

---



---
## 🧠 Démarche de développement

La première étape a consisté à bien comprendre l’objectif du test et à analyser les besoins fonctionnels.

Ayant réalisé mon stage et mon alternance en utilisant Spring Boot, et sachant que le poste visé repose également sur cette technologie, j’ai trouvé ce sujet pertinent et motivant.

Le projet **footballapi** a ensuite été initialisé avec **Spring Initializr**, en utilisant les versions suivantes :
- Java 21
- Spring Boot 3.5.0


### ✅ Dépendances ajoutées dès le départ :
- Spring Web
- Spring Data JPA
- H2 Database
- Validation (aussi appelé “Spring Boot Starter Validation”)
- Lombok
- Spring Boot DevTools

D'autres dépendances ont été ajoutées au cours du développement, en fonction des besoins techniques identifiés.

Le développement a été réalisé avec l’environnement **IntelliJ IDEA**.


---

### 🧱 Première étape : création des entités

La première étape du développement a été la création des entités `Equipe` et `Joueur`.

Celles-ci ont été définies de manière concise et lisible grâce à l’utilisation de la bibliothèque **Lombok**, permettant d’éviter le code répétitif (getters, setters, constructeurs) et de favoriser un **clean code**.

---

### 📚 Deuxième étape : création des Repository

J’ai créé les interfaces :
- `EquipeRepository`
- `JoueurRepository`

Ces deux repositories héritent de `JpaRepository`, ce qui permet de faire les opérations CRUD sans avoir besoin d’écrire de SQL.

- `EquipeRepository` est activement utilisé dans le projet, car la gestion des équipes est centrale.
- `JoueurRepository` n’est pas encore utilisé directement, mais il a été ajouté pour **anticiper l’évolutivité**.

👉 Grâce à la relation `@OneToMany` avec `cascade = CascadeType.ALL`, les joueurs sont automatiquement enregistrés en base lorsqu’on ajoute une équipe.

Ce choix permet à l'application d'évoluer facilement si on veut plus tard :
- Ajouter un joueur sans passer par une équipe
- Modifier un joueur
- Afficher tous les joueurs d’un poste donné

---

### 🧾 Troisième étape : création des DTOs

Deux DTOs ont ensuite été créés pour structurer les échanges avec l’API REST :
- `EquipeDto`
- `JoueurDto`


Les DTOs permettent de :
- Mieux contrôler les données exposées à l’extérieur
- Protéger la structure interne des entités
- Faciliter la validation avec `@NotBlank`, `@NotNull`, `@Min`, etc.

Chaque DTO contient **uniquement** les champs nécessaires pour l’échange via l’API REST.

---

### 🎨 Quatrième étape : création du Mapper

Le mapping entre entités et DTOs a été automatisé à l’aide de **MapStruct**, via la classe `EquipeMapper`, permettant de gérer les conversions suivantes :
- `Equipe ↔ EquipeDto`
- `Joueur ↔ JoueurDto`

Cela évite d’écrire du code de mapping manuel, ce qui rend le code :
- Plus propre
- Plus rapide à maintenir
- Moins sujet aux erreurs

---

### 🧠 Cinquième étape : création du service

La classe `EquipeService` centralise la **logique métier principale** de l’application.

Elle s’appuie sur :
- `EquipeRepository` pour l’accès aux données
- `EquipeMapper` pour assurer la conversion entre entités et DTOs

Les fonctionnalités implémentées incluent :
- L’ajout d’une équipe avec ou sans joueurs
- La recherche d’équipes par nom et/ou plage de budget
- L’affichage paginé et trié des équipes

Un logger **SLF4J** a également été intégré afin de tracer les actions importantes telles que les ajouts ou les recherches.

---

### 🌐 Sixième étape : création du contrôleur REST

Le contrôleur `EquipeController`, annoté avec `@RestController`, expose les points d’entrée de l’API suivants :
- `POST /api/equipes` : permet d’ajouter une équipe avec ou sans joueurs
- `GET /api/equipes` : permet de lister les équipes avec pagination et tri côté serveur

Le contrôleur :
- Reçoit les données JSON
- Les valide avec `@Valid`
- Les transmet au service
- Retourne une réponse formatée en DTO

---
## ✅ Tests

Le projet est intégralement testé à trois niveaux : **service**, **intégration REST**, et **BDD avec JGiven**.

### 🔹 Tests d’intégration – `EquipeIntegrationTest`

Situé dans `integration/EquipeIntegrationTest`, ce test vérifie que l’API REST fonctionne correctement de bout en bout avec :

- Ajout d’une équipe via `POST /equipes` avec des joueurs
- Vérification du `status 201`, du contenu JSON retourné (nom, nombre de joueurs, etc.)
- Insertion directe en base suivie d’un appel `GET /equipes`
- Vérification des données retournées par l’API (nom, acronym, budget, joueurs)

Ce test utilise `MockMvc`, `ObjectMapper`, et simule les requêtes HTTP comme le ferait un client réel.

---

### 🔹 Tests unitaires du service – `EquipeServiceTest`

Situé dans `service/EquipeServiceTest`, il teste la **logique métier** du service :

- Attribution automatique de l’équipe aux joueurs (`testAddEquipe_assignsEquipeToJoueurs`)
- Récupération paginée de toutes les équipes (`testGetAllEquipes_returnsPagedResult`)
- Recherche par nom et budget (`testSearchEquipes_withNameAndBudget_callsCorrectRepoMethod`)

Le test utilise **Mockito** pour simuler les appels aux repository et au mapper.

---

### 🔹 Tests de mapping – `EquipeMapperTest` (avec JGiven)

Les tests du mapper sont situés dans `mapper/EquipeMapperTest` et sont structurés avec **JGiven** selon le modèle Given/When/Then :

- `EquipeMapperGiven` : prépare une équipe complète (nom, budget, acronym, joueur)
- `EquipeMapperWhen` : effectue le mapping de l’entité en DTO
- `EquipeMapperThen` : vérifie que le DTO est correctement rempli (valeurs attendues) ou incorrect si les assertions échouent volontairement

Deux scénarios sont couverts :
- ✅ Mapping correct → DTO contient les bonnes informations
- ❌ Mapping incorrect → test volontairement cassé pour tester la robustesse

---

### 🧪 Lancer les tests

Tu peux lancer tous les tests avec :

```bash
mvn test
```


## 👤 Auteur

Antonio Gerges — Software developer Java / Spring Boot

