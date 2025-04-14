package pl.krupa.dominika.flightbooking.flightreservationsystem.service;

import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightResponse;

import java.util.List;


public interface FlightService {

    FlightResponse getFlightById(Long id);
    FlightResponse getFlightByFlightNumber(String flightNumber);
    List<FlightResponse> getAllFlights();
    FlightResponse createFlight(FlightRequest newFlight);
    FlightResponse updateFlight(FlightRequest updateFlight, long id);
    void deleteFlight(Long id);

}
