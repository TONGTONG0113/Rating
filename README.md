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
