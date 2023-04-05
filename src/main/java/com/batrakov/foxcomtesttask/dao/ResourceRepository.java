package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
}