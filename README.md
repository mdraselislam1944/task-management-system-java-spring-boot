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


## Database Design Schema

### Database Name: `task_management`

The system has two tables: `users` and `posts`, with a one-to-many relationship between them.

### Table 1: `users`

| Column          | Data Type       | Constraints                       |
|-----------------|-----------------|-----------------------------------|
| `id`            | BIGINT (PK)     | Primary Key, Auto-Increment       |
| `email`         | VARCHAR(255)    | NOT NULL, Unique                  |
| `password`      | VARCHAR(255)    | NOT NULL                          |

**Description**:
- The `users` table stores information about each user.
- `id`: Primary key, auto-incremented.
- `email`: Unique and not null, stores the user's email address.
- `password`: Stores the user's password (hashed).

#### SQL to Create `users` Table:
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```

---

### Table 2: `posts`

| Column          | Data Type       | Constraints                                  |
|-----------------|-----------------|----------------------------------------------|
| `id`            | BIGINT (PK)     | Primary Key, Auto-Increment                  |
| `title`         | VARCHAR(255)    | NOT NULL                                    |
| `content`       | TEXT            | NOT NULL                                    |
| `user_id`       | BIGINT (FK)     | Foreign Key references `users(id)`           |

**Description**:
- The `posts` table stores each post created by users.
- `id`: Primary key, auto-incremented.
- `title`: Title of the post.
- `content`: Main body of the post.
- `user_id`: Foreign key that references the `users` table to establish a relationship between posts and users.

#### SQL to Create `posts` Table:
```sql
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

---

### ER Diagram

This is a simple representation of the one-to-many relationship between the `users` and `posts` tables.

```
+--------------+        +------------+
|   users      |        |   posts    |
+--------------+        +------------+
| id (PK)      | 1    N | id (PK)    |
| email        | ------ | title      |
| password     |        | content    |
+--------------+        | user_id (FK)|
                        +------------+
```

---

## API Endpoints

### Users

- **GET /api/v1/users**: Retrieve a list of all users.
- **GET /api/v1/users/{id}**: Retrieve a specific user by ID.
- **POST /api/v1/users**: Create a new user.
- **PUT /api/v1/users/{id}**: Update an existing user by ID.
- **DELETE /api/v1/users/{id}**: Delete a user by ID.

### Posts

- **GET /api/v1/posts**: Retrieve a list of all posts.
- **GET /api/v1/posts/{id}**: Retrieve a specific post by ID.
- **POST /api/v1/posts**: Create a new post.
- **PUT /api/v1/posts/{id}**: Update an existing post by ID.
- **DELETE /api/v1/posts/{id}**: Delete a post by ID.

---

## Running the Application

- Start the Spring Boot application using the following Maven command:
    ```bash
    mvn spring-boot:run
    ```

- Access the API at `http://localhost:8080/api/v1/users` and `http://localhost:8080/api/v1/posts`.

- Ensure the MySQL database is up and running. The application will automatically create the tables based on the JPA entities.

---

### Additional Information

- **CascadeType.ALL** ensures that when a user is deleted, all their associated posts are also deleted.
- **`@JsonManagedReference` and `@JsonBackReference`** are used to avoid infinite recursion when serializing the data between users and posts.

---



















### Error Handling

If a user or post is not found, the API will return a `404 Not Found` error with an appropriate message.

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---



## Conclusion

This Task Management System demonstrates a basic Spring Boot application with JPA and MySQL for managing users and their posts.


