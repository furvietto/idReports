package com.clan.reportsService.repository;

import com.clan.reportsService.entities.JobEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEnt,Integer> {
}
