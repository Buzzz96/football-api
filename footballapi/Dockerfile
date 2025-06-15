# Étape 1 : Image Java légère basée sur Alpine (légère)
FROM eclipse-temurin:21-jdk-alpine

# Étape 2 : Informations sur le créateur (facultatif)
LABEL maintainer="Antonio"

# Étape 3 : Crée le dossier de travail dans le conteneur
WORKDIR /app

# Étape 4 : Copie le fichier JAR de l’application
COPY target/footballapi-0.0.1-SNAPSHOT.jar app.jar

# Étape 5 : Définit la commande de démarrage du conteneur
ENTRYPOINT ["java", "-jar", "app.jar"]
