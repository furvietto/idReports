package com.clan.reportsService.services;

import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.job.BindUserClientRequest;
import com.clan.reportsService.models.report.CreateReportRequest;

public interface JobService {
    ResponseMessage assign(BindUserClientRequest request) throws DataNotValidException;
}
