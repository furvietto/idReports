package com.clan.reportsService.services;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;

import java.util.List;

public interface ClientService {

    CreateClientResponse create(CreateClientRequest request) throws AlreadyExisistingException, DataNotValidException;

    void delete(String name) throws DataNotValidException;
    List<GetAllClientResponse> getAll();
    List<GetAllClientResponse> getAllClientByAccountId(String accountId);
}
