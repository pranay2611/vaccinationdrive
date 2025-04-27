package org.assignment.controller;

import org.assignment.model.VaccinationDrive;
import org.assignment.service.VaccinationDriveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vaccination-drives")
public class VaccinationDriveController {
    private final VaccinationDriveService service;

    public VaccinationDriveController(VaccinationDriveService service) {
        this.service = service;
    }

    @GetMapping
    public List<VaccinationDrive> getAll() {
        return service.findAll()
                .stream()
                .sorted((d1, d2) -> d1.getDriveDate().compareTo(d2.getDriveDate()))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccinationDrive> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VaccinationDrive> create(@RequestBody VaccinationDrive drive) {
        drive.setActive(true);
        return ResponseEntity.ok(service.save(drive));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccinationDrive> update(@PathVariable Long id, @RequestBody VaccinationDrive updatedDrive) {
        Optional<VaccinationDrive> optionalDrive = service.findById(id);

        if (optionalDrive.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        VaccinationDrive existingDrive = optionalDrive.get();

        if (existingDrive.isExpired()) {
            return ResponseEntity.badRequest().build();
        }

        existingDrive.setVaccineName(updatedDrive.getVaccineName());
        existingDrive.setDriveDate(updatedDrive.getDriveDate());
        existingDrive.setAvailableDoses(updatedDrive.getAvailableDoses());
        existingDrive.setApplicableClasses(updatedDrive.getApplicableClasses());
        existingDrive.setActive(updatedDrive.isActive());

        VaccinationDrive savedDrive = service.save(existingDrive);
        return ResponseEntity.ok(savedDrive);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
