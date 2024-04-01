package com.example.StudentToDo.aggregation.mapper.interfaces;

import com.example.StudentToDo.aggregation.entity.Student;
import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.ResponseStudent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapperInterface extends EntityMapping<Student, RequestStudent, ResponseStudent>{
}
