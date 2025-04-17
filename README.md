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
   git clone <repository-url>
   cd <project-folder>

Flight Reservation System

Recruitment Task – Development Department

Project Description

A simple system for managing flight reservations, built in Java as a REST API.The system supports CRUD operations for:

Flights,

Passengers,

Reservations.

Additionally:

When creating a reservation, the system checks if the selected seat is already reserved,

After a reservation is created, an email is sent to the passenger with the reservation number, flight number, and departure date,

Basic data validation and exception handling are included.

Technologies

Java 21

Spring Boot

Spring Data JPA

PostgreSQL

Spring Boot Starter Mail (for sending emails)

Docker, Docker Compose

MapStruct (DTO mapping)

JUnit 5 (unit testing)

Class Structure

Flight

Departure location

Arrival location

Flight duration

Flight number

One-way or round-trip


Passenger

First name

Last name

Email address

Phone number


Reservation

Reservation number

Flight number

Selected seat

Passenger's full name

Passenger's email address

Passenger's phone number

Whether the flight has already taken place


Features

Add new flights, passengers, and reservations

Edit existing flights, passengers, and reservations

Delete flights, passengers, and reservations

Read flight, passenger, and reservation data

Automatically send an email after creating a reservation

Input data validation

Exception handling

How to Run the Application

Prerequisites:

Docker and Docker Compose installed

(Optional) Java 21 and Maven installed if you want to run the application locally without Docker

Steps:

Clone the repository:

git clone <repository-url>
cd <project-folder>

Build the application image:

./mvnw clean package -DskipTests
docker build -t flight-reservation-app .

Start the application and PostgreSQL database using Docker Compose:

docker-compose up

The application will be available at:http://localhost:8080

Available Endpoints

/flights – manage flights

/passengers – manage passengers

/reservations – manage reservations


Testing

Unit tests are located in the src/test/java directory

To run the tests:

./mvnw test

Notes

When creating a reservation, the system checks if the selected seat is available.

After creating a reservation, an email is automatically sent using Spring Boot Starter Mail.

Data is stored in a PostgreSQL database container.

Wersja Polska ↓

System Rezerwacji Lotów

Zadanie rekrutacyjne – Dział Developmentu

Opis Projektu

Prosty system do zarządzania rezerwacjami lotów, zbudowany w Javie jako REST API.System obsługuje operacje CRUD dla:

Lotów,

Pasażerów,

Rezerwacji.

Dodatkowo:

Przy tworzeniu rezerwacji system sprawdza, czy wybrane miejsce nie zostało już wcześniej zarezerwowane,

Po utworzeniu rezerwacji pasażer otrzymuje email z numerem rezerwacji, numerem lotu i datą wylotu,

Zaimplementowana jest podstawowa walidacja danych oraz obsługa błędów.

Technologie

Java 21

Spring Boot

Spring Data JPA

PostgreSQL

Spring Boot Starter Mail (wysyłka emaili)

Docker, Docker Compose

MapStruct (mapowanie DTO)

JUnit 5 (testy jednostkowe)

Struktura Klas

Flight (Lot)

Miejsce wylotu

Miejsce przylotu

Czas trwania lotu

Numer lotu

Lot w jedną stronę lub z powrotem


Passenger (Pasażer)

Imię

Nazwisko

Adres email

Numer telefonu


Reservation (Rezerwacja)

Numer rezerwacji

Numer lotu

Wybrane miejsce

Imię i nazwisko pasażera

Adres email pasażera

Numer telefonu pasażera

Czy wylot już się odbył


Funkcjonalności

Dodawanie nowych lotów, pasażerów i rezerwacji

Edytowanie lotów, pasażerów i rezerwacji

Usuwanie lotów, pasażerów i rezerwacji

Odczytywanie danych lotów, pasażerów i rezerwacji

Automatyczne wysyłanie emaila po utworzeniu rezerwacji

Walidacja danych wejściowych

Obsługa błędów i wyjątków

Jak Uruchomić Aplikację

Wymagania:

Zainstalowany Docker i Docker Compose

(Opcjonalnie) Java 21 i Maven, jeśli chcesz uruchomić aplikację lokalnie

Kroki:

Sklonuj repozytorium:

git clone <adres-repozytorium>
cd <nazwa-folderu>

Zbuduj obraz aplikacji:

./mvnw clean package -DskipTests
docker build -t flight-reservation-app .

Uruchom aplikację i bazę danych PostgreSQL za pomocą Docker Compose:

docker-compose up

Aplikacja dostępna pod adresem:http://localhost:8080

Dostępne Endpointy

/flights — zarządzanie lotami

/passengers — zarządzanie pasażerami

/reservations — zarządzanie rezerwacjami


Testowanie

Testy jednostkowe znajdują się w katalogu src/test/java

Aby uruchomić testy:

./mvnw test

Uwagi

Podczas tworzenia rezerwacji system sprawdza dostępność miejsca.

Po utworzeniu rezerwacji wysyłany jest email za pomocą Spring Boot Starter Mail.

Dane są przechowywane w kontenerze PostgreSQL.

