# Organizer Management Service

## Overview

The Organizer Management Service is a microservice designed to manage special authorities and roles for event organizers in an event ticket booking platform. This service ensures that organizers have the necessary permissions to create, update, and manage events, as well as handle ticket sales and attendee management. The service is built using **Java**, **Spring Boot**, and **MySQL**.

---

## Tech Stack

- **Java 17**  
- **Spring Boot 3.x** (Spring Web, Spring Data JPA, Spring Security)  
- **MySQL** (AWS RDS for production)  
- **Docker** (for containerization)  
- **Postman** (for API Documentation)  
- **Lombok** (for boilerplate code reduction)  
- **JUnit & Mockito** (for unit testing)  

---

## Features

- **Role-based access control** for event organizers  
- Create, update, and delete events  
- Manage ticket sales and pricing  
- Attendee management and check-in  
- Special authorities for premium organizers  
- API documentation using Postman  
- Logging of organizer actions for auditing  
- Error handling for failed operations  

---

## API Endpoints

| Method | Endpoint                                  | Description                                   |
| ------ | ---------------------------------------- | --------------------------------------------- |
| POST   | `/api/organizers/create`                 | Create a new organizer account                |
| PUT    | `/api/organizers/update/{organizerId}`   | Update an existing organizer account          |
| DELETE | `/api/organizers/delete/{organizerId}`   | Delete an organizer account                   |
| POST   | `/api/organizers/{organizerId}/events/{eventId}/tickets` | Manage ticket sales for an event              |
| GET    | `/api/organizers/{organizerId}/events/{eventId}/attendees` | Retrieve attendee list for an event           |
| POST   | `/api/organizers/{organizerId}/events/{eventId}/check-in` | Check-in attendees for an event               |
| POST   | `/api/organizer/{organizerId}/events/{eventId}/advance-book`   | Book ticket in advance for an event         |

---

## Architecture

- **Controller Layer**: Handles API requests for organizer management, event management, ticket sales, and attendee management.  
- **Service Layer**: Implements the business logic for processing organizer actions and managing events.  
- **Repository Layer**: Manages database operations for storing organizer, event, ticket, and attendee records.  

---

## Database Schema

The service uses MySQL with the following tables:  

- **organizers**: Stores organizer data, including special authorities.  
- **events**: Stores event details created by organizers.  
- **tickets**: Stores ticket details for events.  
- **attendees**: Stores attendee details for events.  
- **organizer_actions**: Logs actions performed by organizers for auditing purposes.  

---

## Running Locally

### Prerequisites

- Install **Java 17** and **Maven**.  
- Set up a **MySQL database**.  
- Configure environment variables (e.g., database credentials).  

### Steps

1. Clone the repository:  
   ```bash
   git clone https://github.com/KavanBrahmbhatt0910/BookOnTheGo_Organizer_Management_Service.git
   
2. Build and run the application:  
   bash
   mvn clean install  
   mvn spring-boot:run  

## Testing  

Run unit and integration tests:  
bash
mvn test  

## Contributors  

- Kavankumar Brahmbhatt - [GitHub Profile](https://github.com/KavanBrahmbhatt0910)  

## License  

This project is licensed under the MIT License - see the LICENSE file for details.
