package com.clan.reportsService.models.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateClientRequest {
    private String name;
    private LocalDate creationDate;
    private LocalDate lastUpdate;
}
