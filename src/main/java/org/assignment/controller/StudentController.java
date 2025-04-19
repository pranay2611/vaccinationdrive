package org.assignment.controller;

import org.assignment.dto.VaccinationRecordDTO;
import org.assignment.model.Student;
import org.assignment.model.VaccinationRecord;
import org.assignment.repository.VaccinationRecordRepository;
import org.assignment.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final VaccinationRecordRepository vaccinationRepo;

    public StudentController(StudentService studentService, VaccinationRecordRepository vaccinationRepo) {
        this.studentService = studentService;
        this.vaccinationRepo = vaccinationRepo;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getOne(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student updatedData) {
        return studentService.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(updatedData.getName());
                    existingStudent.setAge(updatedData.getAge());
                    existingStudent.setEmail(updatedData.getEmail());
                    return ResponseEntity.ok(studentService.save(existingStudent));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Student> studentOpt = studentService.findById(id);

        if (studentOpt.isPresent()) {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/vaccination")
    public ResponseEntity<VaccinationRecord> getVaccination(@PathVariable Long id) {
        Optional<Student> studentOptional = studentService.findById(id);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student student = studentOptional.get();
        VaccinationRecord record = student.getVaccinationRecord();
        if (record == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}/update_vaccination")
    public ResponseEntity<VaccinationRecord> updateVaccination(@PathVariable Long id, @RequestBody VaccinationRecordDTO dto) {
        return studentService.findById(id).map(student -> {
            VaccinationRecord record = student.getVaccinationRecord() != null ?
                    student.getVaccinationRecord() :
                    new VaccinationRecord();

            record.setVaccineName(dto.vaccineName());
            record.setDateAdministered(dto.dateAdministered());
            record.setFullyVaccinated(dto.isFullyVaccinated());
            record.setStudent(student);

            VaccinationRecord saved = vaccinationRepo.save(record);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}
