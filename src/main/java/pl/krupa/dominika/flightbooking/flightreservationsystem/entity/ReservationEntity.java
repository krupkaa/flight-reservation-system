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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    private PassengerEntity passenger;

    @Column(name = "selected_seat")
    private String selectedSeat;

    @Column(name = "has_flight_occurred", nullable = false)
    private boolean isDeparture;
}
