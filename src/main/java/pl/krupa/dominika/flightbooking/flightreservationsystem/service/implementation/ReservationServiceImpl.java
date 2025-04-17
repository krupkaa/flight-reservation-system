package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.ReservationService;
import pl.krupa.dominika.flightbooking.flightreservationsystem.validations.SelectedSeatValidation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private SelectedSeatValidation selectedSeatValidation;

    @Override
    public ReservationResponse getReservationById(Long id) {
        ReservationEntity reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with ID " + id + " not found"));

        return reservationMapper.toReservationResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return reservationEntities.stream()
                .map(reservationMapper::toReservationResponse) // Używamy mapera do konwersji na Response
                .collect(Collectors.toList());
    }


    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        FlightEntity flight = flightRepository.findByFlightNumber(request.getFlightNumber())
                .orElseThrow(() -> new FlightNotFoundException("Flight with number " + request.getFlightNumber() + " not found"));

        // Sprawdzenie czy pasażer istnieje
        PassengerEntity passenger = passengerRepository.findByEmail(request.getPassengerEmail())
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with email " + request.getPassengerEmail() + " not found"));

        selectedSeatValidation.validateSeatAvailability(request.getFlightNumber(), request.getSelectedSeat());

        // Mapowanie z request na entity
        ReservationEntity reservationEntity = reservationMapper.toReservationEntity(request, flight, passenger);
        reservationEntity.setReservationNumber(UUID.randomUUID().toString().substring(0, 20).toUpperCase());
        reservationEntity = reservationRepository.save(reservationEntity);

        return reservationMapper.toReservationResponse(reservationEntity);
    }

    @Override
    public ReservationResponse updateReservation(ReservationRequest request, Long id) {
        // Pobranie istniejącej rezerwacji
        ReservationEntity existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with ID " + id + " not found"));

        // Sprawdzenie czy flight istnieje
        FlightEntity flight = flightRepository.findByFlightNumber(request.getFlightNumber())
                .orElseThrow(() -> new FlightNotFoundException("Flight with number " + request.getFlightNumber() + " not found"));

        // Sprawdzenie czy pasażer istnieje
        PassengerEntity passenger = passengerRepository.findById(request.getId())
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with ID " + request.getId() + " not found"));

        selectedSeatValidation.validateSeatAvailability(request.getFlightNumber(), request.getSelectedSeat());
        // Mapowanie danych z requesta na istniejącą rezerwację
        reservationMapper.updateEntityFromRequest(request, existingReservation, flight, passenger);
        reservationRepository.save(existingReservation);

        return reservationMapper.toReservationResponse(existingReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation with id " + id + " not found");
        }
        reservationRepository.deleteById(id);
    }
}
