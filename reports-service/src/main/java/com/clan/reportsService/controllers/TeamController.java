package com.clan.reportsService.controllers;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;
import com.clan.reportsService.models.team.CreateTeamRequest;
import com.clan.reportsService.models.team.CreateTeamResponse;
import com.clan.reportsService.models.team.GetAllTeamResponse;
import com.clan.reportsService.services.TeamService;
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
@RequestMapping("v1/teams")
public class TeamController {

    private TeamService teamService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<CreateTeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(teamService.create(request));
        }
        catch (AlreadyExisistingException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        catch (DataNotValidException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            path = "/getAllTeams",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<List<GetAllTeamResponse>> getAllTeams() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(teamService.getAll());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
