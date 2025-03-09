package com.example.project.Repository.Employee;

import com.example.project.Entity.Employee.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees,Long> {
}
