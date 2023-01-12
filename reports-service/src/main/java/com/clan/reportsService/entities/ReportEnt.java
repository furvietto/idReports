package com.clan.reportsService.entities;

import jakarta.persistence.*;
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
@Table(name = "report")
public class ReportEnt {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "REPORT_SEQ_GEN", sequenceName = "report_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPORT_SEQ_GEN")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    private EmployeeEnt employee;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "body_html",nullable = false,columnDefinition = "TEXT")
    private String text;

    @Column(name = "status",nullable = false)
    private String status;

    @Column(name = "sent_date")
    private LocalDate sentDate;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private ClientEnt client;

    @Column(name = "creation_date",nullable = false)
    private LocalDate creationDate;

    @Column(name = "last_update")
    private LocalDate lastUpdate;


}
