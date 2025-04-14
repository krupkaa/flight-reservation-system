package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationRequest {

    @NotBlank
    @Size(max = 20)
    private String reservationNumber;

    @NotBlank
    @Size(max = 20)
    private String flightNumber;

    @Size(max = 10)
    private String selectedSeat;

    @NotBlank
    @Size(max = 50)
    private String passengerFirstName;

    @NotBlank
    @Size(max = 50)
    private String passengerLastName;

    @NotBlank
    @Size(max = 100)
    private String passengerEmail;

    @NotBlank
    @Size(max = 15)
    private String passengerPhoneNumber;

    private boolean isDeparture;
}
