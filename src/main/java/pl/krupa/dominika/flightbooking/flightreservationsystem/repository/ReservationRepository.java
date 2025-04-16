package pl.krupa.dominika.flightbooking.flightreservationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    boolean existsByFlightFlightNumberAndSelectedSeat(String flightNumber, String selectedSeat);
}
