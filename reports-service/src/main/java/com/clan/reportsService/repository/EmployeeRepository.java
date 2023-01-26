package com.clan.reportsService.repository;

import com.clan.reportsService.entities.EmployeeEnt;
import com.clan.reportsService.entities.TeamEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEnt, Integer> {

    public EmployeeEnt findByAccountId(String id);
}
