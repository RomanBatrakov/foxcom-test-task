package com.batrakov.foxcomtesttask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String ticketSeries;
    private Integer ticketNumber;
    private LocalDate issueDate;
    private LocalDate applicationDate;
    @Enumerated(EnumType.STRING)
    private ApplicationCategory category;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany
    private List<Resource> resources;
}
