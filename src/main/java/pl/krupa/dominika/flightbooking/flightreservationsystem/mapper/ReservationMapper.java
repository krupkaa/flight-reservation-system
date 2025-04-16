package pl.krupa.dominika.flightbooking.flightreservationsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.ReservationEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.ReservationResponse;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    // Rezerwacja -> Response (dla widoku)
    @Mapping(source = "flight.flightNumber", target = "flightNumber")
    @Mapping(source = "passenger.firstName", target = "passengerFirstName")
    @Mapping(source = "passenger.lastName", target = "passengerLastName")
    @Mapping(source = "passenger.email", target = "passengerEmail")
    @Mapping(source = "passenger.phoneNumber", target = "passengerPhoneNumber")
    @Mapping(source = "hasDeparture", target = "departured")
    ReservationResponse toReservationResponse(ReservationEntity reservation);

    @Mapping(source = "flight", target = "flight")
    @Mapping(source = "passenger", target = "passenger")
    @Mapping(target = "id", ignore = true)
    ReservationEntity toReservationEntity(ReservationRequest request, FlightEntity flight, PassengerEntity passenger);

    @Mapping(target = "flight", source = "flight")
    @Mapping(target = "passenger", source = "passenger")
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(ReservationRequest request, @MappingTarget ReservationEntity entity, FlightEntity flight, PassengerEntity passenger);

    ReservationRequest toReservationRequest(ReservationResponse reservationResponse);
}
