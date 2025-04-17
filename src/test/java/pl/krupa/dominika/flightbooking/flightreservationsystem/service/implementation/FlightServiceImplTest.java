package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.FlightMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.FlightRepository;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightMapper flightMapper;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFlightById_shouldReturnFlight() {
        FlightEntity entity = new FlightEntity();
        entity.setId(1L);
        FlightResponse response = new FlightResponse();
        when(flightRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(flightMapper.toFlightResponse(entity)).thenReturn(response);

        FlightResponse result = flightService.getFlightById(1L);
        assertEquals(response, result);
    }

    @Test
    void createFlight_shouldSaveNewFlight() {
        FlightRequest request = new FlightRequest();
        request.setFlightNumber("LOT123");

        FlightEntity newEntity = new FlightEntity();
        FlightEntity savedEntity = new FlightEntity();
        savedEntity.setId(1L);

        FlightResponse response = new FlightResponse();

        when(flightRepository.findByFlightNumber("LOT123")).thenReturn(Optional.empty());
        when(flightMapper.toEntity(request)).thenReturn(newEntity);
        when(flightRepository.save(newEntity)).thenReturn(savedEntity);
        when(flightMapper.toFlightResponse(savedEntity)).thenReturn(response);

        FlightResponse result = flightService.createFlight(request);
        assertEquals(response, result);
    }

    @Test
    void createFlight_shouldThrowException_whenFlightExists() {
        FlightRequest request = new FlightRequest();
        request.setFlightNumber("LOT123");
        when(flightRepository.findByFlightNumber("LOT123")).thenReturn(Optional.of(new FlightEntity()));

        assertThrows(IllegalArgumentException.class, () -> flightService.createFlight(request));
    }

    @Test
    void deleteFlight_shouldCallDelete() {
        when(flightRepository.existsById(1L)).thenReturn(true);
        flightService.deleteFlight(1L);
        verify(flightRepository).deleteById(1L);
    }

    @Test
    void deleteFlight_shouldThrow_whenNotFound() {
        when(flightRepository.existsById(1L)).thenReturn(false);
        assertThrows(RuntimeException.class, () -> flightService.deleteFlight(1L));
    }
}
