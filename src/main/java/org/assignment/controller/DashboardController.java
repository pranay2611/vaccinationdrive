package org.assignment.controller;

import io.jsonwebtoken.Claims;
import org.assignment.dto.DashboardData;
import org.assignment.model.VaccinationDrive;
import org.assignment.repository.StudentRepository;
import org.assignment.repository.VaccinationDriveRepository;
import org.assignment.repository.VaccinationRecordRepository;
import org.assignment.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private VaccinationRecordRepository vaccinationRecordRepository;
    @Autowired
    private VaccinationDriveRepository vaccinationDriveRepository;

    @GetMapping("/data")
    public ResponseEntity<DashboardData> getDashboardData() {
        long totalStudents = studentRepository.count();
        long vaccinatedStudents = vaccinationRecordRepository.countByVaccineNameIsNotNullAndDateAdministeredIsNotNull();
        double vaccinatedPercentage = (totalStudents > 0) ? (vaccinatedStudents * 100.0) / totalStudents : 0.0;
        // Fetch upcoming drive dates from the vaccination_drive table
        List<VaccinationDrive> upcomingDrives = vaccinationDriveRepository.findByDriveDateAfter(LocalDate.now())
                .stream()
                .filter(VaccinationDrive::isActive)
                .sorted((d1, d2) -> d1.getDriveDate().compareTo(d2.getDriveDate()))
                .toList();

        DashboardData dashboardData = new DashboardData(
                totalStudents,
                vaccinatedStudents,
                vaccinatedPercentage,
                upcomingDrives
        );

        return ResponseEntity.ok(dashboardData);
    }
}
