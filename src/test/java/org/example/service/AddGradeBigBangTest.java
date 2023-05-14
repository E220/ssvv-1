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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddGradeBigBangTest {
    private Service service;

    @BeforeEach
    void setupServiceAndEmptyRepository() {
        String filenameGrade = "fisiere/test/Note.xml";
        NotaXMLRepo gradeRepo = new NotaXMLRepo(filenameGrade);
        String filenameStudent = "fisiere/test/Studenti.xml";
        StudentXMLRepo studentRepo = new StudentXMLRepo(filenameStudent);
        String filenameAssignment = "fisiere/test/Teme.xml";
        TemaXMLRepo assignmentRepo = new TemaXMLRepo(filenameAssignment);

        List<Nota> gradeList = new ArrayList<>();
        gradeRepo.findAll().forEach(gradeList::add);
        gradeList.forEach(grade -> gradeRepo.delete(grade.getID()));
        NotaValidator gradeValidator = new NotaValidator(studentRepo, assignmentRepo);

        List<Student> studentList = new ArrayList<>();
        studentRepo.findAll().forEach(studentList::add);
        studentList.forEach(student -> studentRepo.delete(student.getID()));
        StudentValidator studentValidator = new StudentValidator();

        List<Tema> assignmentList = new ArrayList<>();
        assignmentRepo.findAll().forEach(assignmentList::add);
        assignmentList.forEach(assignment -> assignmentRepo.delete(assignment.getID()));
        TemaValidator assignmentValidator = new TemaValidator();

        this.service = new Service(studentRepo, studentValidator, assignmentRepo, assignmentValidator, gradeRepo, gradeValidator);
    }

    @Test
    void studentIdNullAndAssignmentIdNull_shouldThrow() {
        Nota grade = new Nota("1", null, null, 2, LocalDate.now());
        assertThrows(Exception.class, () -> service.addNota(grade, null));
    }

    @Test
    void validStudent_shouldReturnNull() {
        Student student = new Student("1", "1", 1, "1");
        assertNull(service.addStudent(student));
    }

    @Test
    void validAssignment_shouldReturnNull() {
        Tema assignment = new Tema("1", "1", 1, 1);
        assertNull(service.addTema(assignment));
    }

    @Test
    void validCombination_shouldReturnGrade() {
        Student student = new Student("1", "1", 1, "1");
        service.addStudent(student);
        Tema assignment = new Tema("1", "1", 1, 1);
        service.addTema(assignment);

        Nota grade = new Nota("1", "1", "1", 10, LocalDate.of(2018, 10, 8));
        assertEquals(10, service.addNota(grade, "Perfect"));
    }
}
