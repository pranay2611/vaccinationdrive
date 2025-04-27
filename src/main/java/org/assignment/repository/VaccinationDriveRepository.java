package org.assignment.repository;

import org.assignment.model.VaccinationDrive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationDriveRepository extends JpaRepository<VaccinationDrive, Long> {
    List<VaccinationDrive> findByDriveDateBetween(LocalDate startDate, LocalDate endDate);
    List<VaccinationDrive> findByDriveDateAfter(LocalDate date);
}
