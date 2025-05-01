package com.validation.validator;

import com.validation.Student;
import com.validation.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    public void shouldPassValidationForCorrectStudent() {
        Student student = new Student();
        student.setEmail("james.blad@pbs.edu.pl");
        student.setImie("Michał");
        student.setNazwisko("Kardasz");
        student.setNrIndeksu("12235511");

        assertDoesNotThrow(() -> Validator.validate(student));
    }

    @Test
    public void shouldThrowExceptionForInvalidEmail() {
        Student student = new Student();
        student.setEmail("JamesBlad#pbs.edu.pl"); // niepoprawny email
        student.setImie("Michał");
        student.setNazwisko("Kardasz");
        student.setNrIndeksu("12235511");

        Exception exception = assertThrows(ValidationException.class, () -> Validator.validate(student));
        assertTrue(exception.getMessage().toLowerCase().contains("email"));
    }

    @Test
    public void shouldThrowExceptionForBlankFirstName() {
        Student student = new Student();
        student.setEmail("james.blad@pbs.edu.pl");
        student.setImie(""); // pusty string
        student.setNazwisko("Kardasz");
        student.setNrIndeksu("12235511");

        Exception exception = assertThrows(ValidationException.class, () -> Validator.validate(student));
        assertTrue(exception.getMessage().toLowerCase().contains("imię"));
    }

    @Test
    public void shouldThrowExceptionForNullLastName() {
        Student student = new Student();
        student.setEmail("james.blad@pbs.edu.pl");
        student.setImie("Michał");
        student.setNazwisko(null); // null nazwisko
        student.setNrIndeksu("12235511");

        Exception exception = assertThrows(ValidationException.class, () -> Validator.validate(student));
        assertTrue(exception.getMessage().toLowerCase().contains("nazwisko"));
    }

    @Test
    public void shouldThrowExceptionForInvalidIndexNumber() {
        Student student = new Student();
        student.setEmail("james.blad@pbs.edu.pl");
        student.setImie("Michał");
        student.setNazwisko("Kardasz");
        student.setNrIndeksu("abc123"); // niepoprawny indeks

        Exception exception = assertThrows(ValidationException.class, () -> Validator.validate(student));
        assertTrue(exception.getMessage().toLowerCase().contains("indeks"));
    }

    @Test
    public void shouldThrowExceptionForMissingAllFields() {
        Student student = new Student(); // wszystkie pola null

        Exception exception = assertThrows(ValidationException.class, () -> Validator.validate(student));
        assertTrue(exception.getMessage().length() > 0);
    }
}
