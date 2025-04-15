package pl.krupa.dominika.flightbooking.flightreservationsystem.model;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PassengerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;


    public static List<String> getColumnNamesMethod() {
        return Arrays.stream(PassengerResponse.class.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }

}
