package com.example.student.student.project.service;

import com.example.student.student.project.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> allStudents();

    Optional<Student> findById(Long id);

    Student save(Student student);

    void delete(Student student);
}
