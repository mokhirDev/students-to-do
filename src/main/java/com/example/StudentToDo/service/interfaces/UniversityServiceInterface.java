package com.example.StudentToDo.service.interfaces;

import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.university.RequestUniversity;
import com.example.StudentToDo.aggregation.dto.university.ResponseUniversity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityServiceInterface {
    ResponseUniversity registerUniversity(RequestUniversity student);
    ResponseUniversity findUniversityById(String id);
    ResponseMessage remove(String studentId);
    Page<ResponseUniversity> findAllUniversity(Pageable pageable);
    ResponseUniversity modify(RequestUniversity updateStd, String stdId);
    ResponseUniversity findUniversityByName(RequestUniversity university);
}
