package com.clan.reportsService.models.report;

import lombok.Data;

@Data
public class ModifyReportRequest {

    private Integer id;
    private String title;
    private String bodyHtml;
}
