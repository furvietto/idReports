package com.clan.reportsService.repository;

import com.clan.reportsService.entities.ReportEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<ReportEnt,Integer> {

    List<ReportEnt> findAllByEmployee_Id(Integer id);
    }
