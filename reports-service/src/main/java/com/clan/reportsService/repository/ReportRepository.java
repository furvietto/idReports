package com.clan.reportsService.repository;

import com.clan.reportsService.entities.ReportEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEnt,Integer> {
}
