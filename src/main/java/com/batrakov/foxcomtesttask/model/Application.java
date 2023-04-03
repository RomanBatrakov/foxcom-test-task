package com.batrakov.foxcomtesttask.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "hunter_id", referencedColumnName = "id")
    private Hunter hunter;
    @Enumerated(EnumType.STRING)
    private ApplicationCategory category;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;
    @Enumerated(EnumType.STRING)
    private Status status;
}
