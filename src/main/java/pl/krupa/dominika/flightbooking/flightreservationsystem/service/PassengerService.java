package pl.krupa.dominika.flightbooking.flightreservationsystem.service;

import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerResponse;

import java.util.List;

public interface PassengerService {

    PassengerResponse getPassengerById(Long id);
    List<PassengerResponse> getAllPassengers();
    PassengerResponse createPassenger(PassengerRequest request);
    PassengerResponse updatePassenger(PassengerRequest request, Long id);
    void deletePassenger(Long id);
}