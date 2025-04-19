package org.assignment.service;

import org.assignment.model.Student;
import org.assignment.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAll();
    }

    public Optional<Student> findById(Long id) {
        return repo.findById(id);
    }

    public Student save(Student student) {
        return repo.save(student);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
