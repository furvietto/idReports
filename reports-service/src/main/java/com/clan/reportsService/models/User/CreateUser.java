package com.clan.reportsService.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CreateUser implements Serializable {

    private static final Long serialVersionUID = -1L;

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
