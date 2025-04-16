package pl.krupa.dominika.flightbooking.flightreservationsystem.exception;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String message) {
        super(message);
    }
}