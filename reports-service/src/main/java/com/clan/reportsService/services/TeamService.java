package com.clan.reportsService.services;

import com.clan.reportsService.exceptions.general.AlreadyExisistingException;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.client.CreateClientRequest;
import com.clan.reportsService.models.client.CreateClientResponse;
import com.clan.reportsService.models.client.GetAllClientResponse;
import com.clan.reportsService.models.team.CreateTeamRequest;
import com.clan.reportsService.models.team.CreateTeamResponse;
import com.clan.reportsService.models.team.GetAllTeamResponse;

import java.util.List;

public interface TeamService {

    CreateTeamResponse create(CreateTeamRequest request) throws AlreadyExisistingException, DataNotValidException;

    List<GetAllTeamResponse> getAll();

}
