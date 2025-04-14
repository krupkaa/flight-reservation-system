package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;

@Data
public class FlightResponse {

    private String flightNumber;
    private String departureLocation;
    private String arrivalLocation;
    private long durationMinutes;
    private boolean roundTrip;
    private String seatNumber;
}
