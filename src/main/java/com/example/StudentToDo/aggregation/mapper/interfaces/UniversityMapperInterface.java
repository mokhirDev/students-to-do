package com.example.StudentToDo.aggregation.mapper.interfaces;

import com.example.StudentToDo.aggregation.entity.University;
import com.example.StudentToDo.aggregation.dto.university.RequestUniversity;
import com.example.StudentToDo.aggregation.dto.university.ResponseUniversity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapperInterface extends EntityMapping<University, RequestUniversity, ResponseUniversity>{
}
