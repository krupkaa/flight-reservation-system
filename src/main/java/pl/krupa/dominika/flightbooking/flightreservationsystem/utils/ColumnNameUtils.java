package pl.krupa.dominika.flightbooking.flightreservationsystem.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnNameUtils {

    public static List<String> getColumnNamesMethod(Class<?> className) {
        return Arrays.stream(className.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
