package com.batrakov.foxcomtesttask.dao;

import com.batrakov.foxcomtesttask.model.HuntingArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuntingAreaRepository extends JpaRepository<HuntingArea, Long> {
    List<HuntingArea> findHuntingAreaByIdIn(List<Long> huntingAreaIdList);
}