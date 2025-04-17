package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.ReservationEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.FlightNotFoundException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.PassengerNotFoundException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.exception.ReservationNotFoundException;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.ReservationMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.FlightRepository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.PassengerRepository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.ReservationRepository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.validations.SelectedSeatValidation;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ReservationMapper reservationMapper;
    @Mock
    private SelectedSeatValidation selectedSeatValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetReservationById() {
        // given
        ReservationEntity entity = new ReservationEntity();
        ReservationResponse response = new ReservationResponse();
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(reservationMapper.toReservationResponse(entity)).thenReturn(response);

        // when
        ReservationResponse result = reservationService.getReservationById(1L);

        // then
        assertEquals(response, result);
        verify(reservationRepository).findById(1L);
        verify(reservationMapper).toReservationResponse(entity);
    }

    @Test
    void shouldThrowWhenReservationNotFound() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> reservationService.getReservationById(1L));
    }

    @Test
    void shouldGetAllReservations() {
        // given
        List<ReservationEntity> entities = List.of(new ReservationEntity());
        ReservationResponse response = new ReservationResponse();
        when(reservationRepository.findAll()).thenReturn(entities);
        when(reservationMapper.toReservationResponse(any())).thenReturn(response);

        // when
        List<ReservationResponse> result = reservationService.getAllReservations();

        // then
        assertEquals(1, result.size());
        verify(reservationRepository).findAll();
        verify(reservationMapper).toReservationResponse(any());
    }

    @Test
    void shouldCreateReservation() {
        // given
        ReservationRequest request = new ReservationRequest();
        request.setFlightNumber("FL123");
        request.setPassengerEmail("test@example.com");
        request.setSelectedSeat("A1");

        FlightEntity flight = new FlightEntity();
        PassengerEntity passenger = new PassengerEntity();
        ReservationEntity reservationEntity = new ReservationEntity();
        ReservationEntity savedEntity = new ReservationEntity();
        ReservationResponse response = new ReservationResponse();

        when(flightRepository.findByFlightNumber(request.getFlightNumber())).thenReturn(Optional.of(flight));
        when(passengerRepository.findByEmail(request.getPassengerEmail())).thenReturn(Optional.of(passenger));
        when(reservationMapper.toReservationEntity(request, flight, passenger)).thenReturn(reservationEntity);
        when(reservationRepository.save(any())).thenReturn(savedEntity);
        when(reservationMapper.toReservationResponse(savedEntity)).thenReturn(response);

        // when
        ReservationResponse result = reservationService.createReservation(request);

        // then
        assertEquals(response, result);
        verify(flightRepository).findByFlightNumber(request.getFlightNumber());
        verify(passengerRepository).findByEmail(request.getPassengerEmail());
        verify(selectedSeatValidation).validateSeatAvailability(request.getFlightNumber(), request.getSelectedSeat());
        verify(reservationRepository).save(any());
    }

    @Test
    void shouldThrowWhenFlightNotFoundOnCreate() {
        ReservationRequest request = new ReservationRequest();
        request.setFlightNumber("FL123");

        when(flightRepository.findByFlightNumber(request.getFlightNumber())).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> reservationService.createReservation(request));
    }

    @Test
    void shouldThrowWhenPassengerNotFoundOnCreate() {
        ReservationRequest request = new ReservationRequest();
        request.setFlightNumber("FL123");
        request.setPassengerEmail("test@example.com");

        when(flightRepository.findByFlightNumber(request.getFlightNumber())).thenReturn(Optional.of(new FlightEntity()));
        when(passengerRepository.findByEmail(request.getPassengerEmail())).thenReturn(Optional.empty());

        assertThrows(PassengerNotFoundException.class, () -> reservationService.createReservation(request));
    }

    @Test
    void shouldUpdateReservation() {
        ReservationRequest request = new ReservationRequest();
        request.setFlightNumber("FL123");
        request.setId(1L);
        request.setSelectedSeat("A1");

        ReservationEntity existingReservation = new ReservationEntity();
        FlightEntity flight = new FlightEntity();
        PassengerEntity passenger = new PassengerEntity();
        ReservationResponse response = new ReservationResponse();

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(flightRepository.findByFlightNumber(request.getFlightNumber())).thenReturn(Optional.of(flight));
        when(passengerRepository.findById(request.getId())).thenReturn(Optional.of(passenger));
        when(reservationMapper.toReservationResponse(existingReservation)).thenReturn(response);

        // when
        ReservationResponse result = reservationService.updateReservation(request, 1L);

        // then
        assertEquals(response, result);
        verify(selectedSeatValidation).validateSeatAvailability(request.getFlightNumber(), request.getSelectedSeat());
        verify(reservationRepository).save(existingReservation);
    }

    @Test
    void shouldDeleteReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(true);

        reservationService.deleteReservation(1L);

        verify(reservationRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeleteNonExistingReservation() {
        when(reservationRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> reservationService.deleteReservation(1L));
    }
}
