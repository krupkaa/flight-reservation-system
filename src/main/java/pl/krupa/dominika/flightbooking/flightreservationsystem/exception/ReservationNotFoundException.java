package pl.krupa.dominika.flightbooking.flightreservationsystem.exception;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
