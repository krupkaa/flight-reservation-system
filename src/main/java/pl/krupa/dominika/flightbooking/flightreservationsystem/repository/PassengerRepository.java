package pl.krupa.dominika.flightbooking.flightreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
