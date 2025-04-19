package org.assignment.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
//@Data
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
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getDateAdministered() {
        return dateAdministered;
    }

    public void setDateAdministered(LocalDate dateAdministered) {
        this.dateAdministered = dateAdministered;
    }

    public boolean isFullyVaccinated() {
        return isFullyVaccinated;
    }

    public void setFullyVaccinated(boolean fullyVaccinated) {
        isFullyVaccinated = fullyVaccinated;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
