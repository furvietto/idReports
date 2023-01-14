package com.clan.reportsService.controllers;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;
import com.clan.reportsService.services.ClientService;
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
@RequestMapping("v1/client")
public class ClientController {

    private final ClientService clientService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(request));
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
            path = "/getAllClient",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<List<GetAllClientResponse>> getAllClient() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "/delete/{name}"
    )
    private ResponseEntity<Void> delete(@PathVariable("name") String name) {
        try {
            clientService.delete(name);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        catch (DataNotValidException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
