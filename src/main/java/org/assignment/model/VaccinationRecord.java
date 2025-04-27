package org.assignment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private LocalDate dateAdministered;
    private boolean isFullyVaccinated;

    @OneToOne
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private Student student;
}
