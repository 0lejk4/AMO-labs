package com.gelo.amo_labs.repository;

import com.gelo.amo_labs.model.Lab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long>
{
}
