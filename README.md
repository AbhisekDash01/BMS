# 🎬 CineBook – Full-Stack Movie Ticket Booking Platform

CineBook is a full-stack movie ticket booking application built using **Java 21, Spring Boot, MySQL, and JavaScript**. It allows users to explore movies, view available shows, select seats through an interactive interface, and manage their bookings.

🔗 **Live Demo:** https://sage-kulfi-7ff393.netlify.app/

---

## 📌 Project Overview

CineBook is designed to provide a streamlined online movie ticket booking experience.

The application includes movie and show management, interactive seat selection, booking and cancellation workflows, and an administrative interface.

The backend is developed using Spring Boot and exposes REST APIs, while MySQL provides persistent relational data storage.

## ✨ Features

- 🎥 **Movie Management** – Browse and manage movie information.
- 🕒 **Show Management** – View available movie shows.
- 💺 **Interactive Seat Selection** – Select seats using an interactive seat map.
- 🎟️ **Booking Management** – Create and manage movie ticket bookings.
- ❌ **Booking Cancellation** – Cancel bookings and restore seat availability.
- ⚙️ **Admin Panel** – Manage application data through an administrative interface.
- 🔄 **REST API Integration** – Connect frontend operations with backend services.
- 🗄️ **Relational Database** – Organise application data using a structured nine-table MySQL schema.

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Language | Java 21, JavaScript |
| Backend | Spring Boot |
| ORM | Spring Data JPA, Hibernate |
| API | REST APIs |
| Database | MySQL |
| Frontend | HTML, CSS, JavaScript |
| Containerization | Docker |
| Deployment | Render, Aiven, Netlify |

## 🏗️ Architecture

CineBook follows a frontend–backend architecture.

### Deployment Architecture

- **Frontend:** Netlify
- **Backend:** Render (Docker deployment)
- **Database:** Aiven MySQL

The frontend communicates with the Spring Boot backend through REST APIs, while the backend manages application logic and database operations.

## 🗃️ Database Design

The application uses a relational MySQL database consisting of nine tables to organise its core entities and booking-related information.

Database operations are managed using Spring Data JPA and Hibernate entity mappings.

The database design supports:

- Movie and show information.
- Seat availability and booking records.
- Booking and cancellation workflows.
- Relationships between application entities.

  
## 📸 Screenshots
<img width="1887" height="957" alt="Screenshot 2026-10-31 144501" src="https://github.com/user-attachments/assets/b2441b9e-63d8-47c8-9a4c-21ca3d3ab91f" />
<img width="1890" height="925" alt="Screenshot 2026-10-31 144556" src="https://github.com/user-attachments/assets/3464f857-7eda-41d8-a2ab-224f3108ad9d" />
<img width="1897" height="916" alt="Screenshot 2026-10-31 144730" src="https://github.com/user-attachments/assets/a8afd231-b9c8-4cb5-9c38-014308890168" />



## ⚙️ Key Technical Implementations

### 1. Transactional Booking Workflow
Implemented transactional database operations to maintain consistency during seat reservations.

### 2. Seat Availability Management
Managed seat availability during booking and cancellation operations to keep booking information consistent.

### 3. JPA and Hibernate Mapping
Used entity relationships and ORM-based database operations to connect Java application models with MySQL tables.

### 4. REST API Development
Developed backend REST APIs supporting movie listings, show information, seat availability, bookings, and cancellations.

## 📸 Screenshots

Add actual application screenshots here to demonstrate the main interfaces:

- Movie listing page
- Movie details and seat selection page
- Booking management page
- Admin dashboard

## 🚀 Running Locally

### Prerequisites

- Java 21
- MySQL
- Maven
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/AbhisekDash01/CineBook.git
cd CineBook
```

### 2. Configure the Database

Create a MySQL database for the application.

Configure the database connection in the backend's `application.properties` file or through the environment variables supported by the application.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Use your own local credentials. Never commit passwords, API keys, or other secrets to GitHub.

### 3. Run the Backend

Navigate to the backend project directory and execute:

```bash
mvn spring-boot:run
```

If the project includes a Maven wrapper, use:

**Linux/macOS:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

### 4. Run the Frontend

Open the frontend project and follow its included setup instructions to run the application locally.

## 🌐 Deployment

The application is deployed using the following services:

| Component | Platform |
|---|---|
| Frontend | Netlify |
| Backend | Render |
| Database | Aiven MySQL |
| Backend Container | Docker |

🔗 **Live Application:** https://sage-kulfi-7ff393.netlify.app/

## 📚 What I Learned

Through this project, I gained practical experience in:

- Building RESTful backend applications using Spring Boot.
- Designing relational database schemas using MySQL.
- Working with Spring Data JPA, Hibernate, and entity relationships.
- Implementing transactional booking workflows.
- Integrating frontend applications with backend REST APIs.
- Containerising and deploying backend applications.
- Managing application data and deployment configuration.

## 🔮 Future Improvements

- User authentication and role-based authorisation.
- Online payment gateway integration.
- Automated backend testing.
- Improved booking concurrency handling.
- Booking history and user account management.

## 👨‍💻 Author

**Abhisek Dash**

B.Tech Computer Science & Technology Student  
Interested in Java Backend Development and Full-Stack Engineering.

- **GitHub:** [AbhisekDash01](https://github.com/AbhisekDash01)

---

⭐ If you find this project interesting, feel free to explore the repository and live application.
