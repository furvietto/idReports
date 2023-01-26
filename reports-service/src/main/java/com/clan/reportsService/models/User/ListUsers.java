package com.clan.reportsService.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListUsers {

    private String id;
    private String accountId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isLeader;
}
