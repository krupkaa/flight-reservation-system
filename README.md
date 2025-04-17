# Flight Reservation System

**Recruitment Task – Development Department**

## Project Description
A simple system for managing flight reservations, built in Java as a REST API.  
The system supports CRUD operations for:
- Flights
- Passengers
- Reservations

Additionally:
- When creating a reservation, the system checks if the selected seat is already reserved.
- After a reservation is created, an email is sent to the passenger with the reservation number, flight number, and departure date.
- Basic data validation and exception handling are included.

## Technologies
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Spring Boot Starter Mail (for sending emails)
- Docker, Docker Compose
- MapStruct (DTO mapping)
- JUnit 5 (unit testing)

## Class Structure

### Flight
- Departure location
- Arrival location
- Flight duration
- Flight number
- One-way or round-trip

### Passenger
- First name
- Last name
- Email address
- Phone number

### Reservation
- Reservation number
- Flight number
- Selected seat
- Passenger's full name
- Passenger's email address
- Passenger's phone number
- Whether the flight has already taken place

## Features
- Add new flights, passengers, and reservations
- Edit existing flights, passengers, and reservations
- Delete flights, passengers, and reservations
- Read flight, passenger, and reservation data
- Automatically send an email after creating a reservation
- Input data validation
- Exception handling

## How to Run the Application

### Prerequisites:
- Docker and Docker Compose installed
- (Optional) Java 21 and Maven installed if you want to run the application locally without Docker

### Steps:
1. Clone the repository:
   ```bash
   git clone [<repository-url>](https://github.com/krupkaa/flight-reservation-system.git)
   cd flight-reservation-system


