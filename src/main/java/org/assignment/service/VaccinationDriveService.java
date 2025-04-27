package org.assignment.service;

import org.assignment.model.VaccinationDrive;
import org.assignment.repository.VaccinationDriveRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VaccinationDriveService {
    private final VaccinationDriveRepository repository;

    public VaccinationDriveService(VaccinationDriveRepository repository) {
        this.repository = repository;
    }

    public List<VaccinationDrive> findAll() {
        return repository.findAll();
    }

    public Optional<VaccinationDrive> findById(Long id) {
        return repository.findById(id);
    }

    public VaccinationDrive save(VaccinationDrive drive) {
        validateNoOverlap(drive);
        return repository.save(drive);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void updateExpiredDrives() {
        List<VaccinationDrive> drives = repository.findAll();
        for (VaccinationDrive drive : drives) {
            if (drive.getDriveDate().isBefore(LocalDate.now())) {
                drive.setExpired(true);
                repository.save(drive);
            }
        }
    }

    private void validateNoOverlap(VaccinationDrive drive) {
        // Check if the drive date is at least 15 days in advance
        if (drive.getDriveDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Drives must be scheduled at least 15 days in advance.");
        }
    }

}
