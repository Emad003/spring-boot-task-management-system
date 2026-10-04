# Task Management System

A backend Task Management System built using **Spring Boot, Spring Security, Spring Data JPA, and MySQL**.

This project provides secure user authentication, email verification, and task management functionality. Users can create and manage their personal tasks after successful authentication.

The project demonstrates real-world backend development concepts including REST APIs, database relationships, authentication, password encryption, and secure API access.

---

## Features

### Authentication & Security

- User registration
- Email verification system
- Secure password encryption using BCrypt
- User login authentication
- Spring Security integration
- JWT based authentication
- Stateless authentication using JWT tokens
- Protected APIs for authenticated users

### Task Management

- Create tasks
- View tasks
- Update tasks
- Delete tasks
- User-specific task management

### Database

- MySQL database integration
- JPA/Hibernate ORM
- User and Task relationship management

---

# Technology Stack

| Technology | Purpose |
|------------|---------|
| Java | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication and Authorization |
| Spring Data JPA | Database Operations |
| Hibernate | ORM Framework |
| MySQL | Database |
| Maven | Dependency Management |
| REST API | Communication |

---

# Project Architecture

```
Client
  |
  |
REST API Requests
  |
  |
Spring Boot Application
  |
  |------ Controller
  |
  |------ Service Layer
  |
  |------ Repository Layer
  |
  |------ MySQL Database
```

---

# Prerequisites

Before running this project, install:

- Java 17+
- Maven
- MySQL

Check Java:

```bash
java -version
```

Check Maven:

```bash
mvn -version
```

Check MySQL:

```bash
mysql --version
```

---

# Clone Repository

Clone the project:

```bash
git clone https://github.com/Emad003/spring-boot-task-management-system.git
```

Move into the project:

```bash
cd spring-boot-task-management-system
```

---

# Database Configuration

## Step 1: Create Database

Open MySQL and run:

```sql
CREATE DATABASE task_management_system;
```

---

## Step 2: Configure MySQL Connection

Open:

```
src/main/resources/application.properties
```

Update:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/task_management_system

spring.datasource.username=root

spring.datasource.password=YOUR_PASSWORD


spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

Replace:

```
YOUR_PASSWORD
```

with your MySQL password.

---

# Email Configuration

This project uses email verification during user registration.

Add email configuration in:

```
src/main/resources/application.properties
```

Example:

```properties
spring.mail.host=smtp.gmail.com

spring.mail.port=587

spring.mail.username=YOUR_EMAIL@gmail.com

spring.mail.password=YOUR_APP_PASSWORD

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true
```

## Gmail Setup

For Gmail:

1. Enable 2-Step Verification
2. Generate an App Password
3. Use App Password instead of your Gmail password

Never commit email credentials to GitHub.

---

# Run Application

## Using IntelliJ IDEA

1. Clone the repository
2. Open project in IntelliJ IDEA
3. Wait for Maven dependencies
4. Run:

```
TaskManagementSystemApplication.java
```

---

## Using Maven

Run:

```bash
mvn spring-boot:run
```

Application starts:

```
http://localhost:8080
```

---

# API Documentation

## Authentication APIs

### Register User

```
POST /auth/signup
```

Example:

```json
{
    "username":"emad",
    "email":"emad@gmail.com",
    "password":"1234"
}
```

---

### Verify Email

```
GET /auth/verify?token={token}
```

---

### Login User

```
POST /auth/login
```

Example:

```json
{
    "username":"emad",
    "password":"1234"
}
```

---

# Task APIs

## Create Task

```
POST /tasks
```

Example:

```json
{
    "title":"Learn Spring Security",
    "description":"Complete authentication system",
    "completed":false
}
```

---

## Get Tasks

```
GET /tasks
```

---

## Update Task

```
PUT /tasks/{id}
```

---

## Delete Task

```
DELETE /tasks/{id}
```

---

# Database Relationship

A user can have multiple tasks.

```
User
 |
 |---- Task 1
 |
 |---- Task 2
 |
 |---- Task 3
```

Implemented using JPA:

```java
@ManyToOne
private User user;
```

---

# Authentication Flow

```
User Registration
        |
        |
Save User
        |
        |
Send Verification Email
        |
        |
Verify Account
        |
        |
Login
        |
        |
Spring Security Authentication
        |
        |
Access Protected APIs
```

---

# Password Security

Passwords are never stored as plain text.

Flow:

```
User Password

      |
      |
BCrypt Password Encoder

      |
      |
Encrypted Password

      |
      |
MySQL Database
```

---

# Project Structure

```
src/main/java/com/task_management_system

├── controller
│
├── service
│
├── repository
│
├── entity
│
├── dto
│
├── config
│
└── exception
```

---

# Common Issues

## Database Connection Error

Check:

- MySQL server is running
- Database name is correct
- Username and password are correct


## Email Not Sending

Check:

- Gmail App Password
- SMTP configuration
- Email credentials


## Port Already Used

Change:

```properties
server.port=8081
```

in:

```
application.properties
```



# Author

**Md Emad Fazal**

GitHub:

https://github.com/Emad003

---

# License

This project is open-source and available for learning and improvement.
