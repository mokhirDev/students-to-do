package com.example.StudentToDo.repository;

import com.example.StudentToDo.aggregation.entity.StudentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<StudentImage, Long> {
    Optional<StudentImage> findByOriginalName(String imgName);
    void removeByOriginalName(String imgName);
}
