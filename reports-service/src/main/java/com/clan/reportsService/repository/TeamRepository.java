package com.clan.reportsService.repository;

import com.clan.reportsService.entities.TeamEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEnt,Integer> {
}
