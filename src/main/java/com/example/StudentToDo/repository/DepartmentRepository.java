package com.example.StudentToDo.repository;
import com.example.StudentToDo.aggregation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findFirstByName(String firstName);

}
