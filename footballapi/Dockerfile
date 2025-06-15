# Étape 1 : Choisir une image Java officielle légère
FROM eclipse-temurin:21-jdk-alpine

# Étape 2 : Donner des infos sur l’auteur (facultatif)
LABEL maintainer="Antonio"

# Étape 3 : Créer un dossier dans le conteneur pour l'app
WORKDIR /app

# Étape 4 : Copier le .jar depuis ton PC vers le conteneur
COPY target/footballapi-0.0.1-SNAPSHOT.jar app.jar

# Étape 5 : Indiquer la commande à exécuter au lancement
ENTRYPOINT ["java", "-jar", "app.jar"]
