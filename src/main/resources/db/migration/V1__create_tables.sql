CREATE TABLE IF NOT EXISTS flights (
                         id BIGSERIAL PRIMARY KEY,
                         departure_airport VARCHAR(4) NOT NULL,
                         arrival_airport VARCHAR(4) NOT NULL,
                         flight_duration BIGINT NOT NULL,
                         flight_number VARCHAR(10) NOT NULL,
                         direction VARCHAR(1) NOT NULL CHECK (direction IN ('T', 'B', 'T/P'))
);

CREATE TABLE IF NOT EXISTS flights (
                         id BIGSERIAL PRIMARY KEY,
                         departure_airport VARCHAR(4) NOT NULL,
                         arrival_airport VARCHAR(4) NOT NULL,
                         flight_duration BIGINT NOT NULL,
                         flight_number VARCHAR(10) NOT NULL,
                         direction VARCHAR(1) NOT NULL CHECK (direction IN ('T', 'B', 'T/P'))
);

CREATE TABLE IF NOT EXISTS reservations (
                              id BIGSERIAL PRIMARY KEY,
                              reservation_number VARCHAR(255) NOT NULL UNIQUE,
                              flight_id BIGINT NOT NULL,
                              passenger_id BIGINT NOT NULL,
                              selected_seat VARCHAR(50),
                              has_flight_occurred BOOLEAN NOT NULL,
                              CONSTRAINT fk_flight FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE,
                              CONSTRAINT fk_passenger FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id) ON DELETE CASCADE
);

