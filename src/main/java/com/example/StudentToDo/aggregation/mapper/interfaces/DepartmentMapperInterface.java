package com.example.StudentToDo.aggregation.mapper.interfaces;

import com.example.StudentToDo.aggregation.entity.Department;
import com.example.StudentToDo.aggregation.dto.department.RequestDepartment;
import com.example.StudentToDo.aggregation.dto.department.ResponseDepartment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapperInterface extends EntityMapping<Department,
        RequestDepartment, ResponseDepartment>{
}
