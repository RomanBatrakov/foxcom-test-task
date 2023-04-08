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
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "resourceType_id", referencedColumnName = "id")
    private ResourceType resourceType;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    private HuntingArea area;
    private Long amount;
    @ManyToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;
    @Enumerated(EnumType.STRING)
    private Status status;
}
