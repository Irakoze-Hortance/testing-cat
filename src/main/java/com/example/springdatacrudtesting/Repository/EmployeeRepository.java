package com.example.springdatacrudtesting.Repository;

import com.example.springdatacrudtesting.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findById(int id);
}
