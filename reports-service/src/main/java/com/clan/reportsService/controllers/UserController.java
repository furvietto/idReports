package com.clan.reportsService.controllers;

import com.clan.reportsService.models.User.CreateUser;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.User.ResponseMessage;
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
}
