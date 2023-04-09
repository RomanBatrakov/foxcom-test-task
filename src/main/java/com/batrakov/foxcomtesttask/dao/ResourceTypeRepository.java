package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {
    List<ResourceType> findResourceTypeByIdIn(List<Long> resourceTypeIdList);
}
