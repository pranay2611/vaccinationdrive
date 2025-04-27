package org.assignment.service;

import org.assignment.model.Student;
import org.assignment.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public List<Student> findAll() {
        return repo.findAllByOrderByIdAsc();
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

    public void bulkUpload(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(","); // Assuming CSV format
                Student student = Student.builder()
                        .name(data[0])
                        .age(Integer.parseInt(data[1]))
                        .email(data[2])
                        .standards(Integer.parseInt(data[3])) // Parse standards
                        .build();
                repo.save(student);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to process file", e);
        }
    }
}
