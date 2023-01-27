package com.clan.reportsService.services.impls;

import com.clan.reportsService.entities.ClientEnt;
import com.clan.reportsService.entities.EmployeeEnt;
import com.clan.reportsService.entities.JobEnt;
import com.clan.reportsService.exceptions.general.DataNotValidException;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.models.job.BindUserClientRequest;
import com.clan.reportsService.repository.ClientRepository;
import com.clan.reportsService.repository.EmployeeRepository;
import com.clan.reportsService.repository.JobRepository;
import com.clan.reportsService.services.JobService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@AllArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final ClientRepository clientRepository;

    private final EmployeeRepository employeeRepository;
    @Override
    public ResponseMessage assign(BindUserClientRequest request) throws DataNotValidException {
        ResponseMessage responseMessage = new ResponseMessage();
        ClientEnt clientEnt = clientRepository.findById(request.getClientId()).orElse(null);

        EmployeeEnt employeeEnt = employeeRepository.findByAccountId(request.getAccountId());

        JobEnt jobEnt = new JobEnt();
        jobEnt.setClient(clientEnt);
        jobEnt.setEmployee(employeeEnt);
        jobEnt.setCreationDate(LocalDate.parse(request.getCreationDate()));
        jobRepository.save(jobEnt);
        responseMessage.setMessage("assegnazione con con il client avvenuta");
        return responseMessage;
    }
}
