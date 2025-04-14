package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;

@Data
public class PassengerResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
