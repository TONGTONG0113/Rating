# Restaurant Review Platform

A web application for discovering restaurants and sharing user reviews.

This project is developed as part of the **Java / Spring Boot Web Development course at EPF**.

The objective is to build a restaurant review platform with a **public section** and an **administration section**.

---

## Project Overview

The platform allows users to browse restaurants and share their opinions through ratings and reviews.

The application will provide two main sections:

### Public Section

Users can:

- Browse restaurants
- View restaurant information
- View restaurant ratings and reviews
- Leave a rating
- Write a review

### Administration Section

Administrators can:

- Create, update and delete restaurants
- Manage users
- Manage reviews
- Maintain restaurant information

---

## Main Features

### Restaurant Management

Each restaurant contains:

- Name
- Address
- Opening hours

Administrators can:

- Create a restaurant
- View restaurants
- Update restaurant information
- Delete a restaurant

---

### User Management

The application allows user management.

Administrators can:

- Create users
- View users
- Update users
- Delete users

When a user is deleted, their reviews must also be deleted.

---

### Review System

Users can leave a review for a restaurant.

A review contains:

- Rating
- Short summary
- Optional detailed review

Each review is associated with:

- One user
- One restaurant

A restaurant can have multiple reviews.

A user can write multiple reviews.

---


## Getting Started

### Prerequisites

- Git
- Docker Desktop
- Java 17
- Maven
- Node.js and npm

### 1. Clone the repository

```bash
git clone https://github.com/TONGTONG0113/Rating.git
cd skeleton-web-app-school
```

### 2. Configure the database

Go to the backend directory:

```bash
cd back-skeleton
```

Copy `.env.sample` to `.env` and fill in the database configuration:

```dotenv
DATABASE_USER=root
DATABASE_PASSWORD=toor
DATABASE_NAME=default-database
```

These values are examples for local development. Use your own credentials if needed.

### 3. Start PostgreSQL

From the `back-skeleton` directory, run:

```bash
docker compose up -d database
```

On first initialization, PostgreSQL executes the SQL scripts in `initdb/` to create the tables and insert sample data.

**Important:** PostgreSQL initialization scripts run only when the database data directory is first initialized. Existing database volumes are not automatically updated by changing these scripts.

### 4. Start the backend

Set the same database environment variables in the terminal used to launch Spring Boot, then run:

$env:DATABASE_NAME = "default-database"
$env:DATABASE_USER = "root"
$env:DATABASE_PASSWORD = "toor"

mvn spring-boot:run

```

The backend API is available at:

`http://localhost:8080`

### 5. Start the frontend

Open a separate terminal and navigate to `front-skeleton`:

```bash
npm install
npm start
```

Use the local URL printed by Angular in the terminal.

### Troubleshooting

If the backend cannot connect to PostgreSQL, verify that the database container is running, the database credentials match, and the required tables exist.

Do not delete an existing database volume unless you intentionally want to remove its stored data.
## Architecture

The backend follows a layered architecture based on **Spring Boot**, **Spring Data JPA** and **Hibernate**.

```text
                    Client
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
                  PostgreSQL
                      │
                      ▼
                   Docker
