#  Monde de Dév (MDD)

Application full-stack de réseau social pour développeurs, permettant de publier des articles, s'abonner à des thèmes et commenter des publications.

---

## 1. Installer la base de données

Créer la base `mdd` dans MySQL :

```sql
CREATE DATABASE mdd;
```

Configurer le fichier `application.properties` dans `back/src/main/resources` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mdd
spring.datasource.username=VOTRE_UTILISATEUR
spring.datasource.password=VOTRE_MOT_DE_PASSE
spring.jpa.hibernate.ddl-auto=update
```

---

## 2.  Installer l’application

### Back-end (Java Spring Boot)

```bash
cd back
mvn install
```

### Front-end (Angular)

```bash
cd front
npm install
```

---

## 3. ▶ Lancer l’application

### Lancer le back-end

```bash
cd back
mvn spring-boot:run
```

API disponible sur :  
 http://localhost:8080/api

### Lancer le front-end

```bash
cd front
ng serve
```

Application accessible sur :  
 http://localhost:4200

---


##  Technologies utilisées

- Java 17 + Spring Boot  
- Spring Security + JWT  
- MySQL  
- Angular 16+  
- Lombok  