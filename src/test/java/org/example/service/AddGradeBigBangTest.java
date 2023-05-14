package org.example.service;

import org.example.domain.Nota;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepo;
import org.example.repository.StudentXMLRepo;
import org.example.repository.TemaXMLRepo;
import org.example.validation.NotaValidator;
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

        List<Nota> list = new ArrayList<>();
        gradeRepo.findAll().forEach(list::add);
        list.forEach(grade -> gradeRepo.delete(grade.getID()));
        NotaValidator validator = new NotaValidator(studentRepo, assignmentRepo);
        this.service = new Service(null, null, null, null, gradeRepo, validator);
    }

    @Test
    void studentIdNullAndAssignmentIdNull_shouldThrow() {
        Nota grade = new Nota("1", null, null, 2, LocalDate.now());
        assertThrows(Exception.class, () -> service.addNota(grade, null));
    }
}
