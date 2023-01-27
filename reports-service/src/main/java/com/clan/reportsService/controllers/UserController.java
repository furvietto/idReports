package com.clan.reportsService.controllers;

import com.clan.reportsService.models.User.CreateUser;
import com.clan.reportsService.models.User.GetAllUsersTeamLeader;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.report.ReportAdminResponse;
import com.clan.reportsService.models.report.ReportTeamLeaderResponse;
import com.clan.reportsService.services.keycloak.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private KeycloakService keycloakService;

    @RequestMapping(
            method = RequestMethod.POST,
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseMessage> create(@RequestBody CreateUser user) {
        Object[] obj = keycloakService.createUser(user);
        int status = (int) obj[0];
        ResponseMessage message = (ResponseMessage) obj[1];
        return ResponseEntity.status(status).body(message);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/listUsers",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ListUsers>> listUsers() {
        List<ListUsers> users = keycloakService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @RequestMapping(
            method = RequestMethod.GET,
            path = "/getAllUser/{teamId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<List<ListUsers>> getAllUsersByTeamId(@PathVariable("teamId") Integer idTeam) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(keycloakService.getAllUsersByTeamId(idTeam));
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/assignTeamLeader/{accountId}"
    )
    public ResponseEntity<ResponseMessage> assignTeamLeader(@PathVariable("accountId") String accountId) {
    try {
        return ResponseEntity.status(HttpStatus.OK).body(keycloakService.assignTeamLeader(accountId));
    }catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/listReportAdmin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReportAdminResponse>> listReportAdmin() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(keycloakService.listReportAdmin());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/listReportTeamLeader/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ReportTeamLeaderResponse>> listReportTeamLeader(@PathVariable("accountId") String accountId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(keycloakService.listReportTeamLeader(accountId));
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/listUsersTeamLeader/{accountId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<GetAllUsersTeamLeader>> listUsersTeamLeader(@PathVariable("accountId") String accountId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(keycloakService.listUsersTeamLeader(accountId));
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}
