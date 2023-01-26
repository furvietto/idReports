package com.clan.reportsService.services;

import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.report.CreateReportRequest;
import com.clan.reportsService.models.report.GetAllReportResponse;
import com.clan.reportsService.models.report.ModifyReportRequest;

import java.util.List;

public interface ReportService {

    ResponseMessage create(CreateReportRequest request) throws  DataNotValidException;

    List<GetAllReportResponse> getAllReportByAccountId(String accountId);

    ResponseMessage modifyReport(ModifyReportRequest request) throws  DataNotValidException;

}
