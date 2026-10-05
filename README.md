# Restaurant Review Platform

Web application for discovering restaurants and sharing user reviews.

The platform allows users to browse restaurants, view their information and reviews, and submit ratings and comments.

The project also includes an administration section for managing restaurants and users.

---

## Project Objectives

The main objectives of this project are:

- Design a clean and maintainable application architecture
- Build a REST API with Java and Spring Boot
- Use Hibernate / JPA for database persistence
- Implement a relational database
- Develop restaurant management features
- Allow users to submit reviews and ratings
- Provide an administration interface
- Implement additional features such as rankings and statistics

---

# Architecture

The application will follow a layered architecture:

```text
Frontend
    │
    ▼
REST API
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
JPA / Hibernate
    │
    ▼
MySQL Database
```

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Maven

### Database

- MySQL

### Frontend

- Angular

---

# Development Roadmap

## Phase 1 — Project Initialization

- [ ] Create GitHub repository
- [ ] Create README
- [ ] Define project architecture
- [ ] Initialize Spring Boot project
- [ ] Configure Maven
- [ ] Configure Java version
- [ ] Configure MySQL database
- [ ] Configure Spring Boot database connection
- [ ] Verify that the application starts correctly

---

## Phase 2 — Architecture & Best Practices

- [ ] Define package structure
- [ ] Create `controller` layer
- [ ] Create `service` layer
- [ ] Create `repository` layer
- [ ] Create `entity` layer
- [ ] Separate business logic from HTTP logic
- [ ] Use dependency injection
- [ ] Define REST API conventions
- [ ] Define error handling strategy

Expected structure:

```text
src/main/java/
└── com.example.restaurantreview/
    ├── controller/
    ├── service/
    ├── repository/
    ├── entity/
    └── RestaurantReviewApplication.java
```

---

# Phase 3 — Restaurant Management

First main functionality: restaurant management.

### Restaurant entity

Required fields:

- `id`
- `title`
- `address`
- `openingHours`

### Tasks

- [ ] Create `Restaurant` entity
- [ ] Create database table
- [ ] Create `RestaurantRepository`
- [ ] Create `RestaurantService`
- [ ] Create `RestaurantServiceImpl`
- [ ] Create `RestaurantController`
- [ ] Implement `GET /api/restaurants`
- [ ] Implement `GET /api/restaurants/{id}`
- [ ] Implement `POST /api/restaurants`
- [ ] Implement `PUT /api/restaurants/{id}`
- [ ] Implement `DELETE /api/restaurants/{id}`
- [ ] Test all endpoints
- [ ] Verify data persistence in MySQL

### REST API

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/restaurants` | Get all restaurants |
| GET | `/api/restaurants/{id}` | Get one restaurant |
| POST | `/api/restaurants` | Create a restaurant |
| PUT | `/api/restaurants/{id}` | Update a restaurant |
| DELETE | `/api/restaurants/{id}` | Delete a restaurant |

---

# Phase 4 — User Management

Create and manage users.

### User entity

- [ ] Create `User` entity
- [ ] Define user fields
- [ ] Create `UserRepository`
- [ ] Create `UserService`
- [ ] Create `UserController`
- [ ] Create user
- [ ] Get users
- [ ] Update user
- [ ] Delete user
- [ ] Test user management

No authentication is required for this project.

---

# Phase 5 — Review System

Allow users to review restaurants.

### Review entity

Required fields:

- `id`
- `rating`
- `summary`
- `details`
- `createdAt`

Relationships:

```text
User 1 ─────── N Review N ─────── 1 Restaurant
```

### Tasks

- [ ] Create `Review` entity
- [ ] Create relationship with `User`
- [ ] Create relationship with `Restaurant`
- [ ] Create `ReviewRepository`
- [ ] Create `ReviewService`
- [ ] Create `ReviewController`
- [ ] Create a review
- [ ] Display restaurant reviews
- [ ] Update a review
- [ ] Delete a review
- [ ] Validate rating values
- [ ] Test review functionality

---

# Phase 6 — Administration

Create the administration section.

### Restaurant management

- [ ] List restaurants
- [ ] Create restaurant
- [ ] Edit restaurant
- [ ] Delete restaurant

### User management

- [ ] List users
- [ ] Create user
- [ ] Edit user
- [ ] Delete user
- [ ] Delete user's reviews when deleting a user

### Administration dashboard

- [ ] Create dashboard
- [ ] Display number of restaurants
- [ ] Display number of users
- [ ] Display number of reviews

Authentication is not required.

---

# Phase 7 — Public Website

Create the public section.

### Restaurant list

- [ ] Display all restaurants
- [ ] Display restaurant name
- [ ] Display address
- [ ] Display opening hours
- [ ] Display average rating

### Restaurant details

- [ ] Restaurant information
- [ ] Average rating
- [ ] List of reviews
- [ ] Review form

### Navigation

- [ ] Home page
- [ ] Restaurant list
- [ ] Restaurant details
- [ ] Review page
- [ ] Administration section

---

# Phase 8 — Validation & Error Handling

- [ ] Validate restaurant fields
- [ ] Validate user fields
- [ ] Validate review fields
- [ ] Validate rating between 1 and 5
- [ ] Handle `404 Not Found`
- [ ] Handle invalid requests
- [ ] Handle database errors
- [ ] Return appropriate HTTP status codes

---

# Phase 9 — Bonus Features

## Restaurant ranking

- [ ] Calculate average rating
- [ ] Sort restaurants by rating
- [ ] Display best restaurants

## Statistics

- [ ] Display rating distribution
- [ ] Add rating charts
- [ ] Display number of reviews

## Search

- [ ] Search restaurants by name
- [ ] Search restaurants by address
- [ ] Add filters

## UI

- [ ] Improve visual design
- [ ] Responsive design
- [ ] Improve restaurant cards
- [ ] Improve review display

## Email

- [ ] Send email after a certain number of reviews

---

# Testing

- [ ] Test Restaurant CRUD
- [ ] Test User CRUD
- [ ] Test Review CRUD
- [ ] Test relationships
- [ ] Test validation
- [ ] Test error cases
- [ ] Test API endpoints

---

# Team

| Name | Role |
|---|---|
| Member 1 | Backend |
| Member 2 | Frontend |
| Member 3 | Database / Backend |

---

# Technologies

| Technology | Purpose |
|---|---|
| Java | Backend language |
| Spring Boot | Backend framework |
| Spring Web | REST API |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| MySQL | Database |
| Maven | Dependency management |
| Git / GitHub | Version control |
