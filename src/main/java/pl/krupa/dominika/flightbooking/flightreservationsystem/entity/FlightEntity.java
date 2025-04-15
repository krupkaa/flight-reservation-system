package pl.krupa.dominika.flightbooking.flightreservationsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Duration;

@Data
@Entity(name = "flights")
public class FlightEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departure_airport", nullable = false, length = 4)
    private String departureAirport;

    @Column(name = "arrival_airport", nullable = false, length = 4)
    private String arrivalAirport;

    @Column(name = "flight_duration", nullable = false)
    private long duration;

    @Column(name = "flight_number", nullable = false, length = 10)
    private String flightNumber;

    @Column(name = "direction", nullable = false, length = 3)
    //there - T
    //back - B
    //there and back - T/P)
    private DirectionEnum direction;

    @Column(name = "seat_number")
    private String seatNumber;

    public enum DirectionEnum {
        THERE, BACK, THERE_AND_BACK
    }


}
