package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;

import java.time.Duration;

@Data
public class FlightRequest {

    private Long id;

    @NotBlank
    @Size(max = 20)
    private String flightNumber;

    @NotBlank
    @Size(max = 4)
    private String departureAirport;

    @NotBlank
    @Size(max = 4)
    private String arrivalAirport;

    private long duration;  // Duration in minutes

    private FlightEntity.DirectionEnum direction;

//    private String seatNumber;
}
