package org.assignment.dto;

import java.time.LocalDate;

public record VaccinationRecordDTO(
        String vaccineName,
        LocalDate dateAdministered,
        boolean isFullyVaccinated
) { }
