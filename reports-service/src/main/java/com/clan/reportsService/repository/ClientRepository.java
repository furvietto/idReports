package com.clan.reportsService.repository;

import com.clan.reportsService.entities.ClientEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEnt,Integer> {

    public ClientEnt findByName(String name);
}
