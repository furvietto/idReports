package com.clan.reportsService.services.keycloak;

import com.clan.reportsService.entities.EmployeeEnt;
import com.clan.reportsService.entities.TeamEnt;
import com.clan.reportsService.models.User.CreateUser;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.User.ResponseMessage;
import com.clan.reportsService.repository.EmployeeRepository;
import com.clan.reportsService.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${keycloak.auth-server-url}")
    private String server_url;

    @Value("${keycloak.realm}")
    private String realm;

    public Object[] createUser(CreateUser user) {
        ResponseMessage responseMessage = new ResponseMessage();
        int statusId = 0;
        try {
            UsersResource usersResource = getUsersResource();
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(user.getUsername());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);

            Response result = usersResource.create(userRepresentation);
            statusId = result.getStatus();

            if (statusId == 201) {
                String path = result.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf("/") + 1);
                CredentialRepresentation passwordCredential = new CredentialRepresentation();
                passwordCredential.setTemporary(false);
                passwordCredential.setType(CredentialRepresentation.PASSWORD);
                passwordCredential.setValue(user.getPassword());
                usersResource.get(userId).resetPassword(passwordCredential);

                UserResource userResource = usersResource.get(userId);

                //Assegnazione ruolo Realm
                RealmResource realmResource = getRealmResource();
                RoleRepresentation roleRepresentation = realmResource.roles().get("REALM-STANDARD").toRepresentation();


                userResource.roles().realmLevel().add(Arrays.asList(roleRepresentation));

                //assegnazione ruolo Client
                ClientRepresentation app1Client = realmResource.clients().findByClientId("idreports_backend").get(0);

                RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get("STANDARD").toRepresentation();

                userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

                //set LocalDate
                LocalDate creationDate = LocalDate.now();

                //cerco Team
                TeamEnt teamEnt = teamRepository.findByName(user.getTeam());

                //Creazione employeeEnt

                EmployeeEnt employeeEnt = new EmployeeEnt();
                employeeEnt.setAccountId(userId);
                employeeEnt.setTeam(teamEnt);
                employeeEnt.setIsLeader(false);
                employeeEnt.setCreationDate(creationDate);
                employeeRepository.save(employeeEnt);

                responseMessage.setMessage("utente creato con successo");
            }
            else if(statusId == 409) {
                responseMessage.setMessage("l'utente gia esiste");
            }else {
                responseMessage.setMessage("errore nella creazione dell'utente");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[]{statusId, responseMessage};
    }

    public List<ListUsers> getAllUsersByTeamId(Integer idTeam) {
        List<ListUsers> users = new ArrayList<>();
        try {
            TeamEnt teamEnt = teamRepository.findById(idTeam).orElse(null);
            UsersResource usersResource = getUsersResource();
            List<UserRepresentation> userRepresentations = new ArrayList<>();

            //ciclo gli accountId per vedere quali employee mi servono
            for (EmployeeEnt employee : teamEnt.getEmployess()) {
                userRepresentations.add(usersResource.get(employee.getAccountId()).toRepresentation());
            }
            //associo al dto gli attributi da farmi passare
            for (UserRepresentation rep: userRepresentations) {
                ListUsers user = new ListUsers();
                user.setUsername(rep.getUsername());
                user.setAccountId(rep.getId());
                user.setLeader(employeeRepository.findByAccountId(rep.getId()).getIsLeader());
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<ListUsers> getAllUsers() {
        List<ListUsers> responseListUsers = new ArrayList<>();
        try {
            UsersResource usersResource = getUsersResource();
            List<UserRepresentation> users = usersResource.list();
            users.forEach(user -> {
                ListUsers addUser = new ListUsers();
                addUser.setId(user.getId());
                addUser.setUsername(user.getUsername());
                addUser.setEmail(user.getEmail());
                addUser.setFirstName(user.getFirstName());
                addUser.setLastName(user.getLastName());
                responseListUsers.add(addUser);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseListUsers;
    }

    public ResponseMessage assignTeamLeader(String accountId) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {
            UsersResource usersResource = getUsersResource();
            UserResource userResource = usersResource.get(accountId);
            RealmResource realmResource = getRealmResource();

            //prendo i due role dal realm, prima rimuovo lo standard e poi aggiungo il teamLeader
            RoleRepresentation realmStandard = realmResource.roles().get("REALM-STANDARD").toRepresentation();
            RoleRepresentation realmTeamLeader = realmResource.roles().get("REALM-TEAMLEADER").toRepresentation();
            userResource.roles().realmLevel().remove(Arrays.asList(realmStandard));
            userResource.roles().realmLevel().add(Arrays.asList(realmTeamLeader));

            //prendo i due role dal client, prima rimuovo lo standard e poi aggiungo il teamLeader
            ClientRepresentation app1Client = realmResource.clients().findByClientId("idreports_backend").get(0);
            RoleRepresentation clientStandard = realmResource.clients().get(app1Client.getId()).roles().get("STANDARD").toRepresentation();
            RoleRepresentation clientTeamLeader = realmResource.clients().get(app1Client.getId()).roles().get("TEAM-LEADER").toRepresentation();
            userResource.roles().clientLevel(app1Client.getId()).remove(Arrays.asList(clientStandard));
            userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(clientTeamLeader));

            EmployeeEnt employeeEnt = employeeRepository.findByAccountId(accountId);
            employeeEnt.setIsLeader(true);
            employeeRepository.save(employeeEnt);
            responseMessage.setMessage("assegnazione avvenuta");
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage("qualcosa è andato storto");
        }
        return responseMessage;
    }

    private RealmResource getRealmResource() {
        Keycloak kc = KeycloakBuilder.builder().serverUrl(server_url)
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .resteasyClient(new ResteasyClientBuilderImpl().connectionPoolSize(10).build())
                .build();

        return kc.realm(realm);
    }

    private UsersResource getUsersResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
