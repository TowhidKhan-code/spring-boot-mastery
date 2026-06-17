package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository
        extends JpaRepository<Department,Integer> {

    Optional<Department> findByName(String name);

    // Load department WITH employees
    @Query("SELECT d FROM Department d " +
            "LEFT JOIN FETCH d.employees " +
            "WHERE d.id = :id")
    Optional<Department> findByIdWithEmployees(
            @Param("id") Integer id);

}
