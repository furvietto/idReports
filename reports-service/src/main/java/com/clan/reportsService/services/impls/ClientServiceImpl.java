package com.clan.reportsService.services.impls;

import com.clan.reportsService.entities.ClientEnt;
import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;
import com.clan.reportsService.repository.ClientRepository;
import com.clan.reportsService.services.ClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;


    public CreateClientResponse create(CreateClientRequest request) throws AlreadyExisistingException, DataNotValidException {
        if (request.getName() == null || request.getCreationDate() == null) {
            throw new DataNotValidException("dati mancanti");
        }
        if (clientRepository.findByName(request.getName()) == null) {
            LocalDate creationDate = LocalDate.parse(request.getCreationDate());
            ClientEnt entity = new ClientEnt();
            entity.setName(request.getName());
            entity.setCreationDate(creationDate);
            CreateClientResponse response = new CreateClientResponse();
            response.setId(clientRepository.save(entity).getId());
            return response;
        }
        else {
            throw new AlreadyExisistingException("cliente gia esistente");
        }
    }


    public void delete(String name) throws DataNotValidException {
        if (name == null) {
            throw new DataNotValidException("dati mancanti");
        }
        ClientEnt clientEnt = clientRepository.findByName(name);

        clientRepository.deleteById(clientEnt.getId());

    }


    public List<GetAllClientResponse> getAll() {
        List<ClientEnt> entities = clientRepository.findAll();
        List<GetAllClientResponse> response = new ArrayList<>();
        for (ClientEnt client : entities) {
            GetAllClientResponse getAll = new GetAllClientResponse();
            getAll.setId(client.getId());
            getAll.setName(client.getName());
            getAll.setCreationDate(client.getCreationDate());
            getAll.setLastUpdate(client.getLastUpdate());
            response.add(getAll);
        }
        return response;
    }


}
