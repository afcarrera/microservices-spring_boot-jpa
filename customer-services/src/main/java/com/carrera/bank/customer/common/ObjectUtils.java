package com.carrera.bank.customer.common;
import java.lang.reflect.Field;
import java.util.Objects;

public class ObjectUtils {

    /**
     * Copies non-null properties from source object to destination object.
     *
     * @param source the source object
     * @param destination the destination object
     * @throws IllegalAccessException if the field cannot be accessed
     */
    public static void copyNonNullProperties(Object source, Object destination) throws IllegalAccessException {
        if (Objects.isNull(source) || Objects.isNull(destination)) {
            throw new IllegalArgumentException("Source and destination objects must not be null.");
        }

        // Ensure both source and destination are of the same class
        if (!source.getClass().equals(destination.getClass())) {
            throw new IllegalArgumentException("Source and destination objects must be of the same type.");
        }

        // Get all fields (including private) from the source object
        Class<?> currentClass = source.getClass();
        while (currentClass != null && !currentClass.equals(Object.class)) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(source);
                if (Objects.nonNull(value) && !value.toString().isEmpty()) {
                    field.set(destination, value);
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }
}