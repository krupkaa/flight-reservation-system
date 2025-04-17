INSERT INTO flights (departure_airport, arrival_airport, flight_duration, flight_number, direction)
VALUES
    ('EPWA', 'RJTT', 880, 'LO79', 'THERE_AND_BACK'),
    ('EPWA', 'LFPG', 145, 'LO331', 'BACK'),
    ('EPKK', 'EPWA', 50, 'LO3904', 'THERE_AND_BACK');

INSERT INTO passengers (first_name, last_name, email, phone_number)
VALUES
    ('Max', 'Verstappen', 'max@rb.com', '+48123456789'),
    ('Lewis', 'Hamilton', 'lewis@ferrari.com', '+48111222333'),
    ('Lando', 'Noris', 'lando@mclaren.com', '+48778899000');

INSERT INTO reservations (reservation_number, flight_id, passenger_id, selected_seat, has_flight_occurred)
VALUES
    ('49253863EE05', 1, 1, '12A', false),
    ('F8982B49CC7B', 2, 2, '14C', false),
    ('4C0662D31FA3', 3, 3, '2D', false);