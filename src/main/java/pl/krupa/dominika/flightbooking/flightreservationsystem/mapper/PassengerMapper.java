package pl.krupa.dominika.flightbooking.flightreservationsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.PassengerEntity;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerRequest;
import pl.krupa.dominika.flightbooking.flightreservationsystem.model.PassengerResponse;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerResponse toPassengerResponse(PassengerEntity entity);
    PassengerEntity toPassengerEntity(PassengerRequest request);

    PassengerRequest toPassengerRequest(PassengerResponse passengerResponse);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(PassengerRequest request, @MappingTarget PassengerEntity entity);
}
