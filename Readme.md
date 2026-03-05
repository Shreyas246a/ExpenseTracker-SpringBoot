# Expense Tracker API

A RESTful backend API for tracking personal expenses built using Spring Boot.

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT Authentication
* MySQL
* Maven

## Architecture

Controller → Service → Repository → Database

## Features

* User registration and login
* Password reset via email
* JWT authentication
* Create expense
* View expenses
* Delete expenses
* Pagination
* Filtering by category/date

## Not implemented features
* Admin panel
* Expense categories management
* Export expenses to CSV/PDF

## API Endpoints

### Auth

POST /api/auth/register
POST /api/auth/login
POST /api/auth/forgot-password
POST /api/auth/reset-password

### Expenses

POST /api/expenses/expenses
POST /api/expenses/add
PUT /api/expenses/update/{id}
DELETE /api/expenses/delete/{id}

### Dashboard
GET /api/dashboard/summary

## Run Locally

Clone the repository

git clone <repo-url>

## Configuration

Update the following properties in `application.properties` before running the application:

* Database credentials
* JWT secret
* Email configuration

Example:

spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
jwt.secret=your_secret_key


Run the project

mvn spring-boot:run

Server will run on

http://localhost:8081
