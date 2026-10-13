# Rating — Plateforme d'avis sur les restaurants

## 1. Présentation du projet

Rating est une application web permettant aux utilisateurs de découvrir des restaurants de paris, de consulter leurs informations et de visualiser les avis et les classements des restaurants.

## 2. Fonctionnalités fond

### Gestion des restaurants
- Afficher la liste des restaurants.
- Consulter les détails d'un restaurant.
- Ajouter un restaurant.
- Modifier les informations d'un restaurant.
- Supprimer un restaurant.
- Consulter le classement des restaurants.

### Gestion des avis et des notes(cette fonctionnalité affiche dans usermanagement→rewiew)
- Gérer les avis des restaurants.

### Gestion des utilisateurs
- Afficher la liste des utilisateurs.
- Consulter les détails d'un utilisateur.
- Ajouter un utilisateur.
- Modifier les informations d'un utilisateur.
- Supprimer un utilisateur.

### Classement
- Afficher la note moyenne et le nombre d'avis associés à chaque restaurant.
- cliquer le nom de resto on peut aller directement dans la page détail de resto.
  
### page d'accueil
- Affiche tous les restaurants, et classés par type de cuisine (par ex. chinois, français).
- Cliquer sur un restaurant permet d'en afficher les détails, tels que les notes.

## 3. Technologies utilisées

### Frontend
- Angular
- TypeScript
- HTML / SCSS

### Backend
- Java
- Spring Boot
- Spring Data JPA / Hibernate
- Maven
- JUnit 5 et Mockito

### Base de données
- PostgreSQL
- Docker

## 4. Architecture de l'application

L'application repose sur une architecture frontend-backend.

- **Frontend :** Angular fournit l'interface utilisateur et communique avec le backend via une API REST.
- **Couche Controller :** reçoit les requêtes HTTP et renvoie les réponses.
- **Couche Service :** contient la logique métier.
- **Couche DAO :** assure l'accès aux données.
- **Base de données :** PostgreSQL stocke les données de l'application.

Flux général :

Angular → API REST → Controller → Service → DAO → DTO → PostgreSQL

## 5. Base de données

La base de données contient plusieurs tables liées entre elles, notamment :

- `restaurants` : informations sur les restaurants.
- `app_users` : informations sur les utilisateurs.
- `reviews` : avis et notes associés aux utilisateurs et aux restaurants.

Les clés étrangères permettent d'établir les relations entre les tables concernées.
<img width="661" height="500" alt="image" src="https://github.com/user-attachments/assets/f1fb936e-6435-4d5e-83e4-a62ac46222a1" />


### Initialisation de la base de données

Les scripts d'initialisation de la base de données sont fournis dans le dépôt du projet.

Avant de lancer l'application, exécutez les scripts dans l'ordre indiqué dans la documentation du projet.

## 6. Prérequis

Les outils suivants sont nécessaires :

- Java 17, ou la version configurée dans le projet.
- Maven.
- Node.js et npm, compatibles avec le projet Angular.
- Docker et Docker Compose.

## 7. Configuration

Configurez la connexion à la base de données ainsi que les variables d'environnement nécessaires avant de démarrer le backend.

Ne publiez pas de mots de passe, de clés API ou d'autres informations sensibles dans le dépôt Git.

Consultez les fichiers de configuration du projet pour connaître les variables requises.

## 8. Installation et lancement

### Démarrer la base de données

Depuis le répertoire contenant le fichier Docker Compose :

```bash
docker compose up -d
```

### Démarrer le backend

Depuis le répertoire du backend :

```bash
mvn spring-boot:run
```

### Démarrer le frontend

Depuis le répertoire du frontend :

```bash
npm install
ng serve
```

Ouvrez ensuite dans votre navigateur l'adresse locale indiquée par Angular.

Les commandes et les chemins doivent être adaptés à la structure réelle du dépôt.

## 9. API REST

Préfixe de l'API : `/api`

| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/restaurants` | Récupérer tous les restaurants |
| GET | `/restaurants/{id}` | Récupérer un restaurant par son identifiant |
| GET | `/restaurants/ranking` | Récupérer le classement des restaurants |
| POST | `/restaurants` | Créer un restaurant |
| PUT | `/restaurants/{id}` | Modifier un restaurant |
| DELETE | `/restaurants/{id}` | Supprimer un restaurant |

## 10. Tests

Le backend utilise JUnit 5 et Mockito pour les tests unitaires.

Spring Boot Test et MockMvc permettent également de tester les endpoints HTTP.

### Exécuter tous les tests du backend

Depuis le répertoire du backend :

```bash
mvn test
```

### Exécuter les tests du service Restaurant

```bash
mvn -Dtest=AppRestaurantServiceTest test
```

### Exécuter les tests d'intégration Restaurant

```bash
mvn -Dtest=AppRestaurantTest test
```

Les tests doivent être exécutés avant la remise du projet.


## 11. Skill
Présenter ici:

<img width="268" height="69" alt="image" src="https://github.com/user-attachments/assets/4324385f-d73d-4d1e-9b56-9d23aa9b0146" />


## 12. Utilisation de l'intelligence artificielle

### 12.1. Outil utilisé

Tout au long du développement de notre application Rating, nous avons utilisé ChatGPT comme outil d'assistance pour la conception, l'implémentation et la résolution de problèmes techniques, aussi bien pour le frontend que pour le backend.

### 12.2. Utilisation pendant le développement

**Conception et organisation du projet**

Au début du projet, nous avons présenté à ChatGPT les fonctionnalités souhaitées ainsi que les exigences imposées dans le cadre du cours. Nous lui avons demandé de nous proposer une structure générale de l'application, une organisation du frontend et du backend, ainsi qu'une répartition des principales fonctionnalités. Ces propositions nous ont servi de point de départ pour organiser notre travail.

**Développement du frontend**

Pendant le développement du frontend, nous avons utilisé ChatGPT pour nous aider à comprendre et à corriger certaines erreurs, à résoudre des problèmes de code et à améliorer l'implémentation des pages et des fonctionnalités de l'interface utilisateur.

**Développement du backend**

Pour le backend, nous avons utilisé ChatGPT comme aide à la compréhension et à l'implémentation de certaines parties du code, notamment la logique métier, les API REST, l'accès aux données et les interactions entre les différentes couches de l'architecture. Nous l'avons également sollicité pour comprendre des erreurs de compilation et de fonctionnement.

**Préparation des données et des images**

Nous avons utilisé ChatGPT pour générer des données destinées à alimenter notre base de données, notamment des exemples de restaurants et d'avis. Nous avons également eu recours à l'IA pour produire des images destinées à illustrer les restaurants dans l'application.

**Résolution de problèmes techniques et utilisation de Git**

Lorsque nous rencontrions des difficultés que nous ne parvenions pas à résoudre seuls, nous demandions à ChatGPT d'analyser les erreurs et de proposer des solutions. Cela concernait notamment les erreurs de code, les problèmes de configuration et les conflits Git rencontrés lors des opérations de fusion et de `git push`.

### 12.3. Justification de l'utilisation

Le recours à ChatGPT nous a permis de gagner du temps dans certaines tâches, de mieux comprendre des concepts techniques et d'explorer des solutions lorsque nous étions bloqués.

Il nous a également aidés à structurer le projet au début du développement, à préparer des données de test et à identifier plus rapidement certaines erreurs. Cet outil était particulièrement utile lorsque nous rencontrions des problèmes nécessitant des connaissances techniques que nous ne maîtrisions pas encore complètement.

### 12.4. Limites et vérification des résultats

L'utilisation de ChatGPT présente toutefois certaines limites. Les réponses peuvent contenir des erreurs, proposer une solution incompatible avec le code existant ou ne pas tenir compte de toutes les contraintes du projet.

Nous avons donc dû examiner les propositions et les adapter à notre contexte. Les modifications techniques devaient être vérifiées par des tests ou par une vérification du fonctionnement de l'application. Les opérations Git nécessitaient également de la prudence afin de préserver le travail réalisé par les différents membres de l'équipe.

Cette expérience nous a montré qu'une demande précise, accompagnée du code et du contexte nécessaires, permet généralement d'obtenir une aide plus pertinente. Elle nous a aussi appris qu'il est important de comprendre les solutions proposées plutôt que de les appliquer sans vérification.

### 12.5. Bilan et perspectives

ChatGPT a été un outil d'assistance important pendant le développement de notre application, aussi bien pour le frontend que pour le backend. Il nous a aidés à organiser le projet, à résoudre des problèmes techniques et à accélérer certaines tâches de développement.

Cependant, son utilisation ne remplace ni la compréhension du code, ni les tests, ni la validation des choix techniques par l'équipe. Nous restons responsables des fonctionnalités livrées et de leur bon fonctionnement.

À l'avenir, nous souhaitons également explorer une intégration directe de l'intelligence artificielle dans l'application. Nous envisageons notamment un assistant conversationnel capable de recommander des restaurants selon les préférences des utilisateurs, leur budget ou le nombre de personnes, ainsi qu'un système de recherche en langage naturel permettant d'obtenir des informations sur un restaurant, telles que son adresse, sa note moyenne et le nombre d'avis disponibles.



