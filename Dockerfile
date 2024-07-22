# Utilisez une image de base avec Java 11
FROM openjdk:11-jdk-alpine

# Exposez le port de votre application Spring Boot
EXPOSE 8082

# Ajoutez le fichier JAR de votre application dans l'image Docker
ADD target/achat-1.0.jar achat-1.0.jar

# Définissez le point d'entrée de votre application
ENTRYPOINT ["java", "-jar", "/achat-1.0.jar"]
