package pl.krupa.dominika.flightbooking.flightreservationsystem.exception;

public class PassengerNotFoundException extends RuntimeException{

    public PassengerNotFoundException(String message) {
        super(message);
    }
}
