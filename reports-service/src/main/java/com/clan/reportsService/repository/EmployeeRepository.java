package com.clan.reportsService.repository;

import com.clan.reportsService.entities.EmployeeEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEnt, Integer> {
}
