package com.sakshi1001.repository;

import com.sakshi1001.model.PatientReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientReportRepository extends JpaRepository<PatientReport,Long> {
}
