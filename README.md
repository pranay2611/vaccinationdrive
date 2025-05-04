# Vaccination Management System

This project is a **Vaccination Management System** built using **Java** and **Spring Boot**. It provides APIs for managing students, vaccination drives, and vaccination records.

## Features

- **Authentication**: User registration and login with JWT-based authentication.
- **Dashboard**: Provides statistics on total students, vaccinated students, and upcoming vaccination drives.
- **Student Management**: CRUD operations for student data and vaccination records.
- **Vaccination Drive Management**: Manage vaccination drives, including creating, updating, and deleting drives.
- **Bulk Upload**: Upload student data in bulk using a file.

## Technologies Used

- **Java**: Programming language.
- **Spring Boot**: Framework for building the application.
- **Maven**: Dependency management and build tool.
- **JWT**: For secure authentication.
- **PostgreSQL**: Database for development and testing.

## Project Structure

- `src/main/java/org/assignment/controller`: Contains REST controllers for handling API requests.
- `src/main/java/org/assignment/service`: Contains service classes for business logic.
- `src/main/java/org/assignment/repository`: Contains repository interfaces for database operations.
- `src/main/java/org/assignment/model`: Contains entity classes representing database tables.
- `src/main/java/org/assignment/dto`: Contains Data Transfer Objects (DTOs) for API communication.
- `src/main/java/org/assignment/utils`: Contains utility classes like JWT utilities.

## API Endpoints

### Authentication

- **POST** `/auth/signup`: Register a new user.
- **POST** `/auth/login`: Login and receive a JWT token.

### Dashboard

- **GET** `/api/dashboard/data`: Get dashboard statistics.

### Students

- **GET** `/api/students`: Get all students.
- **GET** `/api/students/{id}`: Get a specific student by ID.
- **POST** `/api/students`: Create a new student.
- **PUT** `/api/students/{id}`: Update an existing student.
- **DELETE** `/api/students/{id}`: Delete a student.
- **GET** `/api/students/{id}/vaccination`: Get vaccination record for a student.
- **PUT** `/api/students/{id}/update_vaccination`: Update vaccination record for a student.
- **POST** `/api/students/bulk-upload`: Bulk upload student data.

### Vaccination Drives

- **GET** `/api/vaccination-drives`: Get all vaccination drives.
- **GET** `/api/vaccination-drives/{id}`: Get a specific vaccination drive by ID.
- **POST** `/api/vaccination-drives`: Create a new vaccination drive.
- **PUT** `/api/vaccination-drives/{id}`: Update an existing vaccination drive.
- **DELETE** `/api/vaccination-drives/{id}`: Delete a vaccination drive.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- PostgreSQL 14 or higher

### Installing PostgreSQL

1. **Download and Install**:
   - Visit the [PostgreSQL Downloads](https://www.postgresql.org/download/) page and download the installer for your operating system.
   - Follow the installation instructions provided for your platform.

2. **Set Up Database**:
   - After installation, open the PostgreSQL command-line tool (`psql`) or a GUI tool like pgAdmin.
   - Create a new database:
     ```sql
     CREATE DATABASE vaccinationdrive;
     ```
   - Create a new user and grant privileges:
     ```sql
     CREATE USER vaccinationuser WITH PASSWORD 'vaccinationpass';
     GRANT ALL PRIVILEGES ON DATABASE vaccinationdrive TO vaccinationuser;
     ```

3. **Verify Connection**:
   - Test the connection using the credentials in the `application.properties` file.

### Running the Script for admin user
1**Run the Script**:
   - Use the `psql` command-line tool to execute the script:
     ```bash
     psql -U vaccinationuser -d vaccinationdrive -f src/main/resources/db/admin_user.sql
     ```
   - Alternatively, use a database management tool like pgAdmin to run the script.

2**Verify User**:
   - Check the database to ensure all tables and data have been created as expected.

### Running the Application

1. Clone the repository:
   ```bash
   gh repo clone pranay2611/vaccinationdrive
   cd vaccinationdrive
   mvn clean install
   mvn spring-boot:run