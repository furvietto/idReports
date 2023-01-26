package com.clan.reportsService.controllers;

import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.report.CreateReportRequest;
import com.clan.reportsService.models.report.GetAllReportResponse;
import com.clan.reportsService.models.report.ModifyReportRequest;
import com.clan.reportsService.services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("v1/reports")
public class ReportController {

    private ReportService reportService;
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseMessage> createClient(@RequestBody CreateReportRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(reportService.create(request));
        }
        catch (DataNotValidException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Qualcosa è andato storto"));
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/listReport/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<List<GetAllReportResponse>> getAllReportByAccountId(@PathVariable("accountId") String accountId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reportService.getAllReportByAccountId(accountId));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/modifyReport",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseMessage> modifyReport(@RequestBody ModifyReportRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(reportService.modifyReport(request));
        }
        catch (DataNotValidException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(e.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Qualcosa è andato storto"));
        }
    }
}
