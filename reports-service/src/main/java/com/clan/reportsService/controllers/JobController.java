package com.clan.reportsService.controllers;

import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.job.BindUserClientRequest;
import com.clan.reportsService.models.report.CreateReportRequest;
import com.clan.reportsService.services.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("v1/job")
public class JobController {

    private JobService jobService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/assign",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<ResponseMessage> assignClientEmployee(@RequestBody BindUserClientRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(jobService.assign(request));
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
