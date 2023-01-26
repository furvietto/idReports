package com.clan.reportsService.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUser implements Serializable {

    private static final Long serialVersionUID = -1L;

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String team;

}
