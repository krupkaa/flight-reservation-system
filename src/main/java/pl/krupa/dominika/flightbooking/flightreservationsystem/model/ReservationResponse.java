package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;

@Data
public class ReservationResponse {

    private Long id;
    private String reservationNumber;
    private String flightNumber;
    private String selectedSeat;
    private String passengerFirstName;
    private String passengerLastName;
    private String passengerEmail;
    private String passengerPhoneNumber;
    private boolean departured;

}
