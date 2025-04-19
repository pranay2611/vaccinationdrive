package org.assignment.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String email;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private VaccinationRecord vaccinationRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public VaccinationRecord getVaccinationRecord() {
        return vaccinationRecord;
    }

    public void setVaccinationRecord(VaccinationRecord vaccinationRecord) {
        this.vaccinationRecord = vaccinationRecord;
    }
}
