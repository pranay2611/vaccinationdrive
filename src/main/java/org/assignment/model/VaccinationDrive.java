package org.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VaccinationDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;

    private LocalDate driveDate;

    private int availableDoses;

    private String applicableClasses;

    private boolean isExpired;

    @JsonProperty("active")
    private boolean isActive;

    @PrePersist
    @PreUpdate
    private void validateDriveDate() {
        if (driveDate.isBefore(LocalDate.now().plusDays(15))) {
            throw new IllegalArgumentException("Drives must be scheduled at least 15 days in advance.");
        }
    }
}
