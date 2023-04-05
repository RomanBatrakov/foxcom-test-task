package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {
}