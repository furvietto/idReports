package com.clan.reportsService.models.team;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetAllTeamResponse {

    private Integer id;
    private String name;
    private LocalDate creationDate;
    private LocalDate lastUpdate;
}
