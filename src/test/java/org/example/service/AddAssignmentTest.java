package org.example.service;

import org.example.domain.Tema;
import org.example.repository.TemaXMLRepo;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AddAssignmentTest {
    private Service service;

    @BeforeEach
    void setupServiceAndEmptyRepository() {
        String filenameAssignment = "fisiere/test/Teme.xml";
        TemaXMLRepo repo = new TemaXMLRepo(filenameAssignment);
        List<Tema> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        list.forEach(assignment -> repo.delete(assignment.getID()));
        TemaValidator validator = new TemaValidator();
        this.service = new Service(null, null, repo, validator, null, null);
    }

    @Test
    void idNull_shouldThrowValidationException() {
        Tema assignment = new Tema(null, "1", 1, 1);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void idEmptyString_shouldThrowValidationException() {
        Tema assignment = new Tema("", "1", 1, 1);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void descriptionEmptyString_shouldThrowValidationException() {
        Tema assignment = new Tema("1", "", 1, 1);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void deadlineLessThanOne_shouldThrowValidationException() {
        Tema assignment = new Tema("1", "1", 0, 1);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void deadlineGreaterThanFourteen_shouldThrowValidationException() {
        Tema assignment = new Tema("1", "1", 15, 1);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void deliveryWeekLessThanOne_shouldThrowValidationException() {
        Tema assignment = new Tema("1", "1", 1, 0);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void deliveryDateGreaterThanFourteen_shouldThrowValidationException() {
        Tema assignment = new Tema("1", "1", 1, 15);
        assertThrows(ValidationException.class, () -> service.addTema(assignment));
    }

    @Test
    void correctObject_shouldReturnNull() {
        Tema assignment = new Tema("1", "1", 1, 1);
        assertNull(service.addTema(assignment));
    }
}
