# Utilisez une image de base avec Java 8
FROM openjdk:8-jdk-alpine

# Exposez le port de votre application Spring Boot
EXPOSE 8080

# Définissez le répertoire de travail
WORKDIR /app

# Copiez le fichier jar dans le conteneur
COPY target/achat-1.0.jar /app/achat-1.0.jar

# Exécutez l'application Spring Boot
ENTRYPOINT ["java", "-jar", "achat-1.0.jar"]
