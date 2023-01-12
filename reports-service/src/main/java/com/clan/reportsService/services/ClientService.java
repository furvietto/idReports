package com.clan.reportsService.services;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;

public interface ClientService {

    CreateClientResponse create(CreateClientRequest request) throws AlreadyExisistingException, DataNotValidException;
}
