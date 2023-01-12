package com.clan.reportsService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

//class to create the primary key between client_id and employee_id in JobEnt
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobId implements Serializable {

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobId jobId = (JobId) o;
        return clientId.equals(jobId.clientId) && employeeId.equals(jobId.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, employeeId);
    }
}
