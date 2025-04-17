package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.PassengerMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.PassengerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @InjectMocks
    private PassengerServiceImpl passengerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPassengerById_shouldReturnPassenger_whenExists() {
        PassengerEntity passengerEntity = new PassengerEntity();
        passengerEntity.setId(1L);

        PassengerResponse expectedResponse = new PassengerResponse();
        expectedResponse.setId(1L);

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toPassengerResponse(passengerEntity)).thenReturn(expectedResponse);

        PassengerResponse actualResponse = passengerServiceImpl.getPassengerById(1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getPassengerById_shouldThrowException_whenNotFound() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> passengerServiceImpl.getPassengerById(1L));
    }

    @Test
    void getAllPassengers_shouldReturnListOfPassengers() {
        PassengerEntity passengerEntity1 = new PassengerEntity();
        PassengerEntity passengerEntity2 = new PassengerEntity();

        when(passengerRepository.findAll()).thenReturn(Arrays.asList(passengerEntity1, passengerEntity2));
        when(passengerMapper.toPassengerResponse(any())).thenReturn(new PassengerResponse());

        List<PassengerResponse> passengers = passengerServiceImpl.getAllPassengers();

        assertEquals(2, passengers.size());
    }

    @Test
    void createPassenger_shouldSavePassenger_whenEmailDoesNotExist() {
        PassengerRequest passengerRequest = new PassengerRequest();
        passengerRequest.setEmail("test@example.com");

        PassengerEntity passengerEntity = new PassengerEntity();
        PassengerEntity savedEntity = new PassengerEntity();
        PassengerResponse expectedResponse = new PassengerResponse();

        when(passengerRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passengerMapper.toPassengerEntity(passengerRequest)).thenReturn(passengerEntity);
        when(passengerRepository.save(passengerEntity)).thenReturn(savedEntity);
        when(passengerMapper.toPassengerResponse(savedEntity)).thenReturn(expectedResponse);

        PassengerResponse actualResponse = passengerServiceImpl.createPassenger(passengerRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createPassenger_shouldThrowException_whenEmailExists() {
        PassengerRequest passengerRequest = new PassengerRequest();
        passengerRequest.setEmail("test@example.com");

        when(passengerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new PassengerEntity()));

        assertThrows(IllegalArgumentException.class, () -> passengerServiceImpl.createPassenger(passengerRequest));
    }

    @Test
    void updatePassenger_shouldUpdatePassenger_whenExists() {
        PassengerRequest passengerRequest = new PassengerRequest();
        PassengerEntity passengerEntity = new PassengerEntity();
        PassengerEntity updatedEntity = new PassengerEntity();
        PassengerResponse expectedResponse = new PassengerResponse();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passengerEntity));
        doNothing().when(passengerMapper).updateEntityFromRequest(passengerRequest, passengerEntity);
        when(passengerRepository.save(passengerEntity)).thenReturn(updatedEntity);
        when(passengerMapper.toPassengerResponse(updatedEntity)).thenReturn(expectedResponse);

        PassengerResponse actualResponse = passengerServiceImpl.updatePassenger(passengerRequest, 1L);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updatePassenger_shouldThrowException_whenPassengerNotFound() {
        PassengerRequest passengerRequest = new PassengerRequest();

        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> passengerServiceImpl.updatePassenger(passengerRequest, 1L));
    }

    @Test
    void deletePassenger_shouldDeletePassenger_whenExists() {
        when(passengerRepository.existsById(1L)).thenReturn(true);

        passengerServiceImpl.deletePassenger(1L);

        verify(passengerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePassenger_shouldThrowException_whenPassengerNotFound() {
        when(passengerRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> passengerServiceImpl.deletePassenger(1L));
    }
}
