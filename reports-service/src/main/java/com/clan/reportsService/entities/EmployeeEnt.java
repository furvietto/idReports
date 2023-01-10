package com.clan.reportsService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

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


}
