package com.clan.reportsService.services.impls;

import com.clan.reportsService.entities.ClientEnt;
import com.clan.reportsService.entities.TeamEnt;
import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;
import com.clan.reportsService.models.team.CreateTeamRequest;
import com.clan.reportsService.models.team.CreateTeamResponse;
import com.clan.reportsService.models.team.GetAllTeamResponse;
import com.clan.reportsService.repository.ClientRepository;
import com.clan.reportsService.repository.TeamRepository;
import com.clan.reportsService.services.TeamService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    @Override
    public CreateTeamResponse create(CreateTeamRequest request) throws AlreadyExisistingException, DataNotValidException {
        if (request.getName() == null || request.getCreationDate() == null) {
            throw new DataNotValidException("dati mancanti");
        }
        if (teamRepository.findByName(request.getName()) == null) {
            LocalDate creationDate = LocalDate.parse(request.getCreationDate());
            TeamEnt entity = new TeamEnt();
            entity.setName(request.getName());
            entity.setCreationDate(creationDate);
            CreateTeamResponse response = new CreateTeamResponse();
            response.setId(teamRepository.save(entity).getId());
            return response;
        }
        else {
            throw new AlreadyExisistingException("team gia esistente");
        }

    }

    public List<GetAllTeamResponse> getAll() {
        List<TeamEnt> entities = teamRepository.findAll();
        List<GetAllTeamResponse> response = new ArrayList<>();
        for (TeamEnt client : entities) {
            GetAllTeamResponse getAll = new GetAllTeamResponse();
            getAll.setId(client.getId());
            getAll.setName(client.getName());
            getAll.setCreationDate(client.getCreationDate());
            getAll.setLastUpdate(client.getLastUpdate());
            response.add(getAll);
        }
        return response;
    }



}
