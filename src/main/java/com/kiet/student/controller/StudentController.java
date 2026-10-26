package com.kiet.student.controller;

import com.kiet.student.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>(List.of(
            new Student(101, "Rahul Sharma", "rahul@kiet.edu", "CSE", 2, 8.65),
            new Student(102, "Priya Verma", "priya@kiet.edu", "CSE", 2, 9.12),
            new Student(103, "Aman Gupta", "aman@kiet.edu", "IT", 3, 8.20),
            new Student(104, "Sneha Singh", "sneha@kiet.edu", "CSE(AI & ML)", 2, 9.25),
            new Student(105, "Arjun Kumar", "arjun@kiet.edu", "ECE", 1, 7.90)
    ));

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable int studentId) {
        Optional<Student> student = findStudent(studentId);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return notFound();
    }

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        if (findStudent(student.getStudentId()).isPresent()) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("message", "Student ID already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable int studentId,
                                           @RequestBody Student updatedStudent) {
        Optional<Student> existing = findStudent(studentId);
        if (existing.isEmpty()) {
            return notFound();
        }

        // The ID in the URL is authoritative, even if the JSON body contains a different ID.
        updatedStudent.setStudentId(studentId);
        int index = students.indexOf(existing.get());
        students.set(index, updatedStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable int studentId) {
        Optional<Student> student = findStudent(studentId);
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message("Student not found"));
        }
        students.remove(student.get());
        return ResponseEntity.ok(message("Student deleted successfully"));
    }

    @GetMapping("/search")
    public List<Student> searchByBranchAndYear(@RequestParam String branch,
                                               @RequestParam int year) {
        return students.stream()
                .filter(student -> student.getBranch().equalsIgnoreCase(branch))
                .filter(student -> student.getYear() == year)
                .toList();
    }

    @GetMapping("/topper")
    public List<Student> findByMinimumCgpa(@RequestParam double cgpa) {
        return students.stream()
                .filter(student -> student.getCgpa() >= cgpa)
                .toList();
    }

    private Optional<Student> findStudent(int studentId) {
        return students.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst();
    }

    private ResponseEntity<Map<String, String>> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message("Student not found"));
    }

    private Map<String, String> message(String text) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", text);
        return response;
    }
}
