package com.validation;
import com.validation.exception.ValidationException;
import com.validation.validator.Validator;

public class Main {
	public static void main(String[] args) {
		try {
			Student student = new Student();
			student.setEmail("JamesBlad#pbs.edu.pl");
			student.setImie("michal");
			student.setNazwisko("kardasz");
			student.setNrIndeksu("12235511");
			//TODO przetestuj kolejne adnotacje ustawiając błędne wartości
			Validator.validate(student);
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
		}
	}
}