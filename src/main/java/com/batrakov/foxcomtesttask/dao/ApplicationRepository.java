package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.Application;
import com.batrakov.foxcomtesttask.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStatusIn(Collection<Status> statuses);
    List<Application> findByStatus(Status status);
}
