package com.example.student.student.project;

import com.example.student.student.project.exception.ResourceNotFoundException;
import com.example.student.student.project.model.Student;
import com.example.student.student.project.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentService.allStudents();
    }

    @DeleteMapping("/students/{id}")
    public Map<String,Boolean> deleteStudent(@PathVariable(value="id") Long studentId)
        throws ResourceNotFoundException {
        Student student = studentService.findById(studentId)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found for this id "+ studentId));

        studentService.delete(student);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;

    }

    @PostMapping("/students/create")
    public Student saveStudent(@Valid @RequestBody Student student){
        return studentService.save(student);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable(value="id") Long studentId)
    throws ResourceNotFoundException{
        Student student = studentService.findById(studentId).
                orElseThrow(()-> new ResourceNotFoundException("Student not found for this id "+ studentId));
        return ResponseEntity.ok().body(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long studentId,
                                                 @Valid @RequestBody Student studentDetails)
    throws ResourceNotFoundException{
        Student student = studentService.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this Id "+ studentId));

        student.setName(studentDetails.getName());
        student.setAge(studentDetails.getAge());
        student.setDob(studentDetails.getDob());

        final Student updateStudent = studentService.save(student);
        return ResponseEntity.ok(updateStudent);
    }


}
