package pl.krupa.dominika.flightbooking.flightreservationsystem.exception;

public class SeatAlreadyReservedException extends RuntimeException {

    public SeatAlreadyReservedException(String message) {
        super(message);
    }
}
