package pl.krupa.dominika.flightbooking.flightreservationsystem.service;

import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationResponse;

import java.util.List;

public interface ReservationService {

    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getAllReservations();
    ReservationResponse createReservation(ReservationRequest request);
    ReservationResponse updateReservation(ReservationRequest request, Long id);
    void deleteReservation(Long id);
}
