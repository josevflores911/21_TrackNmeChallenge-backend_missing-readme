package com.tracknme.firstTry.repository;

import com.tracknme.firstTry.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>, Serializable {
    Optional<Employee> findById(String id);

    //Optional<Employee> findByCep(Integer employeeCep);

    List<Employee> findByCep(Integer EmployeeCep);

    //List<Employee> getByCep(Integer cepNumber);
}
