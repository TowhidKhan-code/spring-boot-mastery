package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    List<Employee> findByDepartmentId(Integer departmentId);

    List<Employee> findBySalaryGreaterThan(Double salary);

    List<Employee> findByPosition(String position);
}
