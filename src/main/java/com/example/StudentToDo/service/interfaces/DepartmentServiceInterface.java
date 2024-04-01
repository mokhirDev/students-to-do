package com.example.StudentToDo.service.interfaces;

import com.example.StudentToDo.aggregation.dto.department.RequestDepartment;
import com.example.StudentToDo.aggregation.dto.department.ResponseDepartment;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface DepartmentServiceInterface {
    ResponseDepartment add(RequestDepartment department);
    ResponseDepartment findById(String id);
    List<ResponseDepartment> findByName(String name);
    ResponseMessage removeById(String departmentId);
    Page<ResponseDepartment> findAll(Pageable pageable);
    ResponseDepartment update(RequestDepartment updateDep);
}
