package com.validation.strategy;

import java.lang.reflect.Field;
import java.util.Optional;
import com.validation.annotation.Size;

public class SizeStrategy implements ValidationStrategy {

    @Override
    public Optional<String> validate(Field field, Object value) {
        if (field.isAnnotationPresent(Size.class)) {
            Size annotation = field.getAnnotation(Size.class);
            if (value == null || !(value instanceof String)) {
                String errorInfo = String.format("Pole %s: %s", field.getName(), annotation.message());
                return Optional.of(errorInfo);
            }

            String str = value.toString();
            int length = str.length();

            if (length < annotation.min() || length > annotation.max()) {
                String errorInfo = annotation.message()
                    .replace("{min}", String.valueOf(annotation.min()))
                    .replace("{max}", String.valueOf(annotation.max()));
                errorInfo = String.format("Pole %s: %s", field.getName(), errorInfo);
                return Optional.of(errorInfo);
            }
        }
        return Optional.empty();
    }
}
