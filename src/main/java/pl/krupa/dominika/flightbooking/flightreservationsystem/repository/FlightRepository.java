package pl.krupa.dominika.flightbooking.flightreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<FlightRepository, Long> {

    Optional<FlightEntity> findById(Long flightId);

    Optional<FlightEntity> findByFlightNumber(String flightNumber);
}
