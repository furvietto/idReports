package com.clan.reportsService.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "job")
public class JobEnt {
    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @SequenceGenerator(name = "JOB_SEQ_GEN", sequenceName = "job_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "JOB_SEQ_GEN")
    private JobId id;

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    @MapsId("employeeId")
    private EmployeeEnt employee;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    @MapsId("clientId")
    private ClientEnt client;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_update")
    private LocalDate lastUpdate;


}
