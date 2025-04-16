package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservationRequest {

    private Long id;

    @NotBlank
    @Size(max = 20)
    private String reservationNumber;

    @NotBlank
    @Size(max = 20)
    private String flightNumber;

    @Size(max = 10)
    @Pattern(regexp = "\\d{2}[A-J]", message = "Seat must be in format: two digits followed by a letter A-J (e.g. 01A, 10B)")
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
