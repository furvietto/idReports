package com.clan.reportsService.models.job;

import lombok.Data;

@Data
public class BindUserClientRequest {
    private String accountId;
    private Integer clientId;
    private String creationDate;
}
