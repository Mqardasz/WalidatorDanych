package com.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.validation.exception.ValidationException;
import com.validation.strategy.ValidationStrategy;
import com.validation.strategy.ValidationStrategyFactory;

public class Validator {

    private Validator() {
        // Konstruktor prywatny, aby uniemożliwić tworzenie instancji
    }

    public static void validate(Object object) throws ValidationException {
        final List<String> errors = new ArrayList<>();
        final Class<?> clazz = object.getClass();

        // Iterowanie po wszystkich polach danej klasy
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true); // Umożliwia dostęp do pól prywatnych
            try {
                Object value = field.get(object);

                // Iterowanie po wszystkich adnotacjach danego pola
                for (Annotation annotation : field.getAnnotations()) {
                    ValidationStrategy strategy = ValidationStrategyFactory.getStrategy(annotation);
                    if (strategy != null) {
                        Optional<String> validationError = strategy.validate(field, value);
                        validationError.ifPresent(errors::add);
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Błąd dostępu do pola: " + field.getName());
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(String.join("\n", errors));
        }
    }
}
