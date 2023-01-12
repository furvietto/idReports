package com.clan.reportsService.controllers;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.services.ClientService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("v1/clients")
public class ClientController {

    private final ClientService clientService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @RolesAllowed("ADMIN")
    private ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request));
        }
        catch (AlreadyExisistingException e) {
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
}
