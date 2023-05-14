package org.example.service;

import org.example.domain.Student;
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
        NotaValidator gradeValidator = new NotaValidator(studentRepo, assignmentRepo);
        service = new Service(studentRepo, new StudentValidator(), assignmentRepo, new TemaValidator(), gradeRepo, gradeValidator);
    }

    @Test
    public void studentNameNull_shouldThrowValidationException() {
        Student student = new Student("1", null, 1, "1");
        assertThrows(ValidationException.class, () -> service.addStudent(student));
    }
}
