package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;
import pl.krupa.dominika.flightbooking.flightreservationsystem.entity.FlightEntity;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FlightResponse {


    private Long id;
    private String flightNumber;
    private String departureAirport;
    private String arrivalAirport;
    private long duration;
    private FlightEntity.DirectionEnum direction;
    private String seatNumber;

    public static List<String> getColumnNamesMethod() {
        return Arrays.stream(FlightResponse.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
