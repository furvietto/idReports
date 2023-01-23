package com.clan.reportsService.services.keycloak;

import com.clan.reportsService.models.User.CreateUser;
import com.clan.reportsService.models.User.ListUsers;
import com.clan.reportsService.models.User.ResponseMessage;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakService {

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

                RealmResource realmResource = getRealmResource();
                RoleRepresentation roleRepresentation = realmResource.roles().get("REALM-STANDARD").toRepresentation();


                userResource.roles().realmLevel().add(Arrays.asList(roleRepresentation));

                ClientRepresentation app1Client = realmResource.clients().findByClientId("idreports_backend").get(0);

                RoleRepresentation userClientRole = realmResource.clients().get(app1Client.getId()).roles().get("STANDARD").toRepresentation();

                userResource.roles().clientLevel(app1Client.getId()).add(Arrays.asList(userClientRole));

                //List<UserRepresentation> users = usersResource.list();

               // List<UserRepresentation> search = usersResource.searchByFirstName("pippo",false);

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

    public List<ListUsers> getAllUsers() {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> users = usersResource.list();
        List<ListUsers> responseListUsers = new ArrayList<>();

        users.forEach(user -> {
            ListUsers addUser = new ListUsers();
            addUser.setId(user.getId());
            addUser.setUsername(user.getUsername());
            addUser.setEmail(user.getEmail());
            addUser.setFirstName(user.getFirstName());
            addUser.setLastName(user.getLastName());
            responseListUsers.add(addUser);
        });
        return responseListUsers;
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
