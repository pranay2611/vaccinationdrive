package org.assignment.repository;

import org.assignment.model.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {
}
