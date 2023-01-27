package com.clan.reportsService.models.report;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReportRequest {

    private String employeeId;
    private String clientName;
    private String title;
    private String bodyHtml;
    private String status;
    private String creationDate;

}
