package com.clan.reportsService.repository;

import com.clan.reportsService.entities.EmployeeEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEnt, Integer> {
}
