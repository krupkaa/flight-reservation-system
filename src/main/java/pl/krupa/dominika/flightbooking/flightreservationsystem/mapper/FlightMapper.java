package pl.krupa.dominika.flightbooking.flightreservationsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.FlightResponse;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightEntity toEntity(FlightRequest request);
    FlightResponse toFlightResponse(FlightEntity entity);
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(FlightRequest request, @MappingTarget FlightEntity entity);
}
