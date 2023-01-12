package com.clan.reportsService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEnt {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TEAM_SEQ_GEN", sequenceName = "team_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GEN")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<EmployeeEnt> employess;


    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_update")
    private LocalDate lastUpdate;


}
