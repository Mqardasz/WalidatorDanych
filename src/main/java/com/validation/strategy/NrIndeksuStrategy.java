package com.validation.strategy;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.regex.Pattern;
import com.validation.annotation.NrIndeksu;

public class NrIndeksuStrategy implements ValidationStrategy {

    private static final Pattern INDEX_PATTERN = Pattern.compile("^\\d{8}$");

    @Override
    public Optional<String> validate(Field field, Object value) {
        if (field.isAnnotationPresent(NrIndeksu.class)) {
            if (value == null || !INDEX_PATTERN.matcher(value.toString()).matches()) {
                NrIndeksu annotation = field.getAnnotation(NrIndeksu.class);
                String errorInfo = String.format("Pole %s: %s", field.getName(), annotation.message());
                return Optional.of(errorInfo);
            }
        }
        return Optional.empty();
    }
}
