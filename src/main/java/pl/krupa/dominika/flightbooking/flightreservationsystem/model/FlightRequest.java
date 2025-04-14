package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class FlightRequest {

    @NotBlank
    @Size(max = 20)
    private String flightNumber;

    @NotBlank
    @Size(max = 100)
    private String departureLocation;

    @NotBlank
    @Size(max = 100)
    private String arrivalLocation;

    private long durationMinutes;  // Duration in minutes

    private boolean roundTrip;

    private String seatNumber;
}
