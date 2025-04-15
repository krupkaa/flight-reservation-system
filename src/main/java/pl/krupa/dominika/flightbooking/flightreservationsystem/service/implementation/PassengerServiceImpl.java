package pl.krupa.dominika.flightbooking.flightreservationsystem.service.implementation;

import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.mapper.PassengerMapper;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerResponse;
import pl.krupa.dominika.flightbooking.flightreservationsystem.repository.PassengerRepository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

    @Override
    public PassengerResponse getPassengerById(Long id) {
        PassengerEntity passengerEntity = passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with the given ID does not exist"));
        return passengerMapper.toPassengerResponse(passengerEntity);
    }

    @Override
    public List<PassengerResponse> getAllPassengers() {
        List<PassengerEntity> passengerEntities = passengerRepository.findAll();
        return passengerEntities.stream()
                .map(passengerMapper::toPassengerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerResponse createPassenger(PassengerRequest newPassenger) {
        if (passengerRepository.findByEmail(newPassenger.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Passenger with this email already exists!");
        }

        PassengerEntity newPassengerEntity = passengerMapper.toPassengerEntity(newPassenger);
        PassengerEntity savedPassenger = passengerRepository.save(newPassengerEntity);
        return passengerMapper.toPassengerResponse(savedPassenger);
    }

    @Override
    public PassengerResponse updatePassenger(PassengerRequest updatePassenger, Long id) {
        PassengerEntity existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Passenger with the given ID does not exist"));

        passengerMapper.updateEntityFromRequest(updatePassenger, existingPassenger);

        PassengerEntity updatedPassenger = passengerRepository.save(existingPassenger);
        return passengerMapper.toPassengerResponse(updatedPassenger);
    }

    @Override
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new RuntimeException("Passenger with id " + id + " not found");
        }
        passengerRepository.deleteById(id);
    }
}
