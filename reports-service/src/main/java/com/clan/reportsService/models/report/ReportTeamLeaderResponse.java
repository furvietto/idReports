package com.clan.reportsService.models.report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportTeamLeaderResponse {

    private Integer id;
    private String teamName;
    private String username;
    private String title;
    private String bodyHtml;
    private String status;
    private LocalDate creationDate;

}
