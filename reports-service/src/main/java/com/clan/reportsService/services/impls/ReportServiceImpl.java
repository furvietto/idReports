package com.clan.reportsService.services.impls;

import com.clan.reportsService.entities.ClientEnt;
import com.clan.reportsService.entities.EmployeeEnt;
import com.clan.reportsService.entities.ReportEnt;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.report.CreateReportRequest;
import com.clan.reportsService.models.report.GetAllReportResponse;
import com.clan.reportsService.models.report.ModifyReportRequest;
import com.clan.reportsService.repository.ClientRepository;
import com.clan.reportsService.repository.EmployeeRepository;
import com.clan.reportsService.repository.ReportRepository;
import com.clan.reportsService.services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    private final ClientRepository clientRepository;

    private EmployeeRepository employeeRepository;
    @Override
    public ResponseMessage create(CreateReportRequest request) throws DataNotValidException {
        if (request.getTitle() == null || request.getBodyHtml() == null || request.getStatus() == null || request.getCreationDate() == null) {
            throw new DataNotValidException("Dati inconsistenti");
        }
            ResponseMessage responseMessage = new ResponseMessage();

            LocalDate creationDateParse = LocalDate.parse(request.getCreationDate());
            EmployeeEnt employee = employeeRepository.findByAccountId(request.getEmployeeId());
            ClientEnt clientEnt = clientRepository.findByName(request.getClientName());
            ReportEnt report = new ReportEnt();
            report.setEmployee(employee);
            report.setTitle(request.getTitle());
            report.setBodyHtml(request.getBodyHtml());
            report.setStatus(request.getStatus());
            report.setCreationDate(creationDateParse);
            report.setClient(clientEnt);
            reportRepository.save(report);
            responseMessage.setMessage("report mandato");

        return responseMessage;
    }

    @Override
    public List<GetAllReportResponse> getAllReportByAccountId(String accountId) {
        EmployeeEnt employee = employeeRepository.findByAccountId(accountId);
        List<ReportEnt> reports = reportRepository.findAllByEmployee_Id(employee.getId());
        List<GetAllReportResponse> response = new ArrayList<>();
        for (ReportEnt report: reports) {
            GetAllReportResponse getAllReportResponse = new GetAllReportResponse();
            getAllReportResponse.setId(report.getId());
            getAllReportResponse.setTitle(report.getTitle());
            getAllReportResponse.setBodyHtml(report.getBodyHtml());
            getAllReportResponse.setStatus(report.getStatus());
            getAllReportResponse.setCreationDate(report.getCreationDate());
            if(report.getClient() != null) {
                getAllReportResponse.setClientName(report.getClient().getName());
            }
            response.add(getAllReportResponse);
        }
        return response;
    }

    @Override
    public ResponseMessage modifyReport(ModifyReportRequest request) throws DataNotValidException {
        ResponseMessage responseMessage = new ResponseMessage();
        ReportEnt reportEnt = reportRepository.findById(request.getId()).orElse(null);
        reportEnt.setTitle(request.getTitle());
        reportEnt.setBodyHtml(request.getBodyHtml());
        reportEnt.setStatus("SENT");
        reportRepository.save(reportEnt);
        responseMessage.setMessage("Modifica avvenuta");
        return responseMessage;
    }
}
