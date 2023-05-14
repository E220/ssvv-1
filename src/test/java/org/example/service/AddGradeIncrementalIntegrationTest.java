package org.example.service;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AddGradeIncrementalIntegrationTest {
    private Service service;

    @Mock
    private StudentXMLRepo studentRepo;

    @Mock
    private NotaXMLRepo gradeRepo;

    @Mock
    private TemaXMLRepo assignmentRepo;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
        NotaValidator gradeValidator = new NotaValidator(studentRepo, assignmentRepo);
        service = new Service(studentRepo, new StudentValidator(), assignmentRepo, new TemaValidator(), gradeRepo, gradeValidator);
    }

    @Test
    public void studentNameNull_shouldThrowValidationException() {
        Student student = new Student("1", null, 1, "1");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    public void validStudentValidAssignment_shouldReturnNull() {
        Student student = new Student("1", "1", 1, "1");
        assertNull(service.addStudent(student));

        Tema assignment = new Tema("1", "1", 1, 1);
        assertNull(service.addTema(assignment));
    }

    @Test
    public void validEverything_shouldReturnsGrade() {
        Student student = new Student("1", "1", 1, "1");
        assertNull(service.addStudent(student));
        Mockito.when(studentRepo.findOne(Mockito.anyString())).thenReturn(student);

        Tema assignment = new Tema("1", "1", 1, 1);
        assertNull(service.addTema(assignment));
        Mockito.when(assignmentRepo.findOne(Mockito.anyString())).thenReturn(assignment);

        Nota grade = new Nota("1", "1", "1", 6.9, LocalDate.of(2018, 10, 8));
        assertEquals(6.9, service.addNota(grade, "Nice"));
    }
}
