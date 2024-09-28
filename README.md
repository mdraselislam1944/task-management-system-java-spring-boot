# User Management System

A Spring Boot-based REST API for managing users and their posts, supporting bi-directional relationships between users and posts.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Technologies](#technologies)
- [Requirements](#requirements)
- [Setup](#setup)
  - [Clone the repository](#clone-the-repository)
  - [Database setup](#database-setup)
  - [Run the application](#run-the-application)
- [Usage](#usage)
  - [API Endpoints](#api-endpoints)
  - [Sample JSON Data](#sample-json-data)
- [License](#license)

## Features
- Manage users and their associated posts.
- Bi-directional relationship handling between users and posts.
- Prevent infinite recursion using `@JsonManagedReference` and `@JsonBackReference`.
- RESTful endpoints for creating, retrieving, and managing users and posts.

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/user_management/
│   │       ├── controller/
│   │       │   ├── UserController.java
│   │       │   └── PostController.java
│   │       ├── model/
│   │       │   ├── UserModel.java
│   │       │   └── PostModel.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   └── PostRepository.java
│   │       └── service/
│   │           ├── UserService.java
│   │           └── PostService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

## Technologies

- **Spring Boot**: Framework for building RESTful APIs.
- **JPA/Hibernate**: Object Relational Mapping (ORM) for database interaction.
- **H2** or **MySQL**: Can be used as a database (depending on configuration).
- **Jackson**: Handles JSON serialization and deserialization.

## Requirements
- **Java 17+**: Make sure you have the correct version of Java installed.
- **Maven**: Dependency management tool.
- **Database**: (MySQL or H2)

## Setup

### Clone the repository

```bash
git clone https://github.com/your-repo/user-management-system.git
cd user-management-system
```

### Database Setup

You can configure the database in the `src/main/resources/application.properties` file. For this example, we are using MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_management
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

If you prefer H2 as an in-memory database, update `application.properties` like this:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the Application

Use Maven to build and run the application:

```bash
mvn clean install
mvn spring-boot:run
```

The application should be running at [http://localhost:5000](http://localhost:5000).

## Usage

### API Endpoints

Here are the available API endpoints:

- **Users**:
  - `GET /api/v1/users`: Retrieve all users.
  - `GET /api/v1/users/{id}`: Retrieve a specific user by ID.
  - `POST /api/v1/users`: Create a new user.
  - `PUT /api/v1/users/{id}`: Update an existing user.
  - `DELETE /api/v1/users/{id}`: Delete a user by ID.

- **Posts**:
  - `GET /api/v1/users/{userId}/posts`: Retrieve all posts for a specific user.
  - `POST /api/v1/users/{userId}/posts`: Create a new post for a specific user.

### Sample JSON Data

#### Create a New User

```json
POST /api/v1/users
{
    "email": "user@example.com",
    "password": "password123"
}
```

#### Create a New Post for a User

```json
POST /api/v1/users/1/posts
{
    "title": "My First Post",
    "content": "This is the content of my first post."
}
```

#### Get All Users with Posts

```json
GET /api/v1/users
[
    {
        "id": 1,
        "email": "user@example.com",
        "password": "password123",
        "posts": [
            {
                "id": 1,
                "title": "My First Post",
                "content": "This is the content of my first post."
            }
        ]
    }
]
```

### Error Handling

If a user or post is not found, the API will return a `404 Not Found` error with an appropriate message.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---



