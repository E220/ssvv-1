package org.example.service;

import org.example.domain.Student;
import org.example.repository.StudentXMLRepo;
import org.example.validation.StudentValidator;
import org.example.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentTest {
    private Service service;
    @BeforeEach
    void setupServiceAndEmptyRepository() {
        String filenameStudent = "fisiere/test/Studenti.xml";
        StudentXMLRepo repo = new StudentXMLRepo(filenameStudent);
        List<Student> list = new ArrayList<>();
        repo.findAll().forEach(list::add);
        list.forEach(student -> repo.delete(student.getID()));
        StudentValidator validator = new StudentValidator();
        this.service = new Service(repo, validator, null, null, null, null);
    }
    @Test
    void groupNumberPositive_shouldReturnNull() {
        Student student = new Student("test1", "Sanyi", 1, "sanyi@gmail.com");
        assertNull(this.service.addStudent(student));
    }

    @Test
    void groupNumberNegative_shouldThrow() {
        assertThrows(ValidationException.class, () -> {
            Student student = new Student("test2", "Sanyi", -1, "sanyi@gmail.com");
            this.service.addStudent(student);
        });
    }
}