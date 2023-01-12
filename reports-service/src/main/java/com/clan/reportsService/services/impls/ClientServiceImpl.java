package com.clan.reportsService.services.impls;

import com.clan.reportsService.entities.ClientEnt;
import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.repository.ClientRepository;
import com.clan.reportsService.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Override
    public CreateClientResponse create(CreateClientRequest request) throws AlreadyExisistingException, DataNotValidException {
        if (request.getName() == null || request.getCreationDate() == null) {
            throw new DataNotValidException("dati mancanti");
        }
        if (clientRepository.findByName(request.getName()) == null) {
            ClientEnt entity = new ClientEnt();
            entity.setName(request.getName());
            entity.setCreationDate(request.getCreationDate());
            entity.setLastUpdate(request.getLastUpdate());
            CreateClientResponse response = new CreateClientResponse();
            response.setId(clientRepository.save(entity).getId());
            return response;
        }
        else {
            throw new AlreadyExisistingException("cliente gia esistente");
        }
    }
}
