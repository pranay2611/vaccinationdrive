package org.assignment.dto;

import org.assignment.model.VaccinationDrive;

import java.util.List;

public record DashboardData(
        long totalStudents,
        long vaccinatedStudents,
        double vaccinatedPercentage,
        List<VaccinationDrive> upcomingDrives
) { }
