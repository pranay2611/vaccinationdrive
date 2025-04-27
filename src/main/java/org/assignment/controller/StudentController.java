package org.assignment.controller;

import org.assignment.dto.VaccinationRecordDTO;
import org.assignment.model.Student;
import org.assignment.model.VaccinationRecord;
import org.assignment.repository.StudentRepository;
import org.assignment.repository.VaccinationRecordRepository;
import org.assignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    @Autowired
    private VaccinationRecordRepository vaccinationRepo;
    @Autowired
    private StudentRepository studentRepository;

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
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id).map(existingStudent -> {
            // Update student fields
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setAge(updatedStudent.getAge());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setStandards(updatedStudent.getStandards());

            // Update vaccination record
            if (updatedStudent.getVaccinationRecord() != null) {
                VaccinationRecord updatedRecord = updatedStudent.getVaccinationRecord();
                VaccinationRecord existingRecord = existingStudent.getVaccinationRecord();

                if (existingRecord != null) {
                    existingRecord.setVaccineName(updatedRecord.getVaccineName());
                    existingRecord.setDateAdministered(updatedRecord.getDateAdministered());
                    existingRecord.setFullyVaccinated(updatedRecord.isFullyVaccinated());
                } else {
                    updatedRecord.setStudent(existingStudent); // Associate with persistent student
                    existingStudent.setVaccinationRecord(updatedRecord);
                }
            }

            // Save updated student
            Student savedStudent = studentRepository.save(existingStudent);
            return ResponseEntity.ok(savedStudent);
        }).orElse(ResponseEntity.notFound().build());
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
            // Ensure the student is saved before associating it with the vaccination record
            if (student.getId() == null) {
                student = studentRepository.save(student);
            }

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

    @PostMapping("/bulk-upload")
    public ResponseEntity<Void> bulkUpload(@RequestParam("file") MultipartFile file) {
        studentService.bulkUpload(file);
        return ResponseEntity.ok().build();
    }
}
