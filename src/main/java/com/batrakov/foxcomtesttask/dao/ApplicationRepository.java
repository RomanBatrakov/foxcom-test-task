package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
