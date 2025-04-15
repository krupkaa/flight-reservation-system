package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.FlightMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.FlightRepository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.FlightService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightMapper flightMapper;

    @Override
    public FlightResponse getFlightById(Long id) {
        FlightEntity flightEntity = flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The flight with the given ID does not exist"));
        return flightMapper.toFlightResponse(flightEntity);
    }

    @Override
    public FlightResponse getFlightByFlightNumber(String flightNumber) {
        FlightEntity flightEntity = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new IllegalArgumentException("The flight with the given FlightNumber does not exist"));
        return flightMapper.toFlightResponse(flightEntity);
    }

    @Override
    public List<FlightResponse> getAllFlights() {
        List<FlightEntity> flightEntities = flightRepository.findAll();
        return flightEntities.stream()
                .map(flightMapper::toFlightResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightResponse createFlight(FlightRequest newFlight) {
        Optional<FlightEntity> existingFlight = flightRepository.findByFlightNumber(newFlight.getFlightNumber());
        if (existingFlight.isPresent()) {
            throw new IllegalArgumentException("Flight already exists!");
        }

        FlightEntity newFlightEntity = flightMapper.toEntity(newFlight);
        FlightEntity savedFlight = flightRepository.save(newFlightEntity);
        return flightMapper.toFlightResponse(savedFlight);
    }

    @Override
    public FlightResponse updateFlight(FlightRequest updateFlight, long id) {
        FlightEntity existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("The flight with the given ID does not exist"));

        flightMapper.updateEntityFromRequest(updateFlight, existingFlight);  // Assuming you have this method in your mapper

        FlightEntity updatedFlight = flightRepository.save(existingFlight);
        return flightMapper.toFlightResponse(updatedFlight);
    }

    @Override
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new RuntimeException("Flight with id " + id + " not found");
        }
        flightRepository.deleteById(id);
    }
}




