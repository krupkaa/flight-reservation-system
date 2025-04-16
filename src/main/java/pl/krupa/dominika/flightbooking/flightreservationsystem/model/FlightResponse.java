package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;

@Data
public class FlightResponse {


    private Long id;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private long duration;
    private FlightEntity.DirectionEnum direction;
    private String seatNumber;
}
