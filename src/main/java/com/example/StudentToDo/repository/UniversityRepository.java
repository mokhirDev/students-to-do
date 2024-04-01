package com.example.StudentToDo.repository;

import com.example.StudentToDo.aggregation.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
    University findByName(String name);
}
