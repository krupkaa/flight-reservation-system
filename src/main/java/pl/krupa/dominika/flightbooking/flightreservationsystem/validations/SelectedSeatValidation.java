package pl.krupa.dominika.flightbooking.flightreservationsystem.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.SeatAlreadyReservedException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.ReservationRepository;

@Service
public class SelectedSeatValidation {

    @Autowired
    private ReservationRepository reservationRepository;

    public void validateSeatAvailability(String flightNumber, String selectedSeat) {
        boolean isSeatTaken = reservationRepository.existsByFlightFlightNumberAndSelectedSeat(flightNumber, selectedSeat);

        if (isSeatTaken) {
            throw new SeatAlreadyReservedException("The selected seat " + selectedSeat + " is already reserved for flight " + flightNumber);
        }
    }
}
