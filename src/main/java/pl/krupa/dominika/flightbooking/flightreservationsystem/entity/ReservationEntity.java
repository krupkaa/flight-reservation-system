package pl.krupa.dominika.flightbooking.flightreservationsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_number", nullable = false, unique = true, length = 20)
    private String reservationNumber;

    @Column(name = "flight_number", nullable = false, length = 20)
    private String flightNumber;

    @Column(name = "selected_seat")
    private String selectedSeat;

    @Column(name = "passenger_first_name", nullable = false, length = 50)
    private String passengerFirstName;

    @Column(name = "passenger_last_name", nullable = false, length = 50)
    private String passengerLastName;

    @Column(name = "passenger_email", nullable = false, length = 100)
    private String passengerEmail;

    @Column(name = "passenger_phone_number", nullable = false, length = 15)
    private String passengerPhoneNumber;

    @Column(name = "has_flight_occurred", nullable = false)
    private boolean isDeparture;

}
