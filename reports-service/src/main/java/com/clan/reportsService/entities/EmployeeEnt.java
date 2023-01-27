package com.clan.reportsService.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEnt {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "EMPLOYEE_SEQ_GEN", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_SEQ_GEN")
    private Integer id;

    @Column(name = "account_id",nullable = false)
    private String accountId;

    @ManyToOne
    @JoinColumn(name="team_id",nullable = false)
    private TeamEnt team;

    @OneToMany(mappedBy = "employee")
    private Set<ReportEnt> reports;

    @OneToMany(mappedBy = "employee")
    private Set<JobEnt> jobs;

    @Column(name = "is_leader",nullable = false)
    private Boolean isLeader;

    @Column(name = "creation_date",nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_update")
    private LocalDate lastUpdate;


}
