package com.tracknme.firstTry.repository;

import com.tracknme.firstTry.entities.CepDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CepRepository extends JpaRepository<CepDetails, Integer>, Serializable {
}
