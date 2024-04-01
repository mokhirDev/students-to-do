package com.example.StudentToDo.aggregation.mapper;

import com.example.StudentToDo.aggregation.dto.university.RequestUniversity;
import com.example.StudentToDo.aggregation.dto.university.ResponseUniversity;
import com.example.StudentToDo.aggregation.entity.University;
import com.example.StudentToDo.aggregation.mapper.interfaces.UniversityMapperInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Component
@Primary
public class UniversityMapper implements UniversityMapperInterface {
    @Override
    public University toEntity(RequestUniversity req) {
        if (req == null) {
            return null;
        }

        University.UniversityBuilder university = University.builder();
        university.name(req.getName());
        return university.build();
    }

    @Override
    public ResponseUniversity toDto(University entity) {
        if (entity == null) {
            return null;
        }
        ResponseUniversity.ResponseUniversityBuilder responseUniversity = ResponseUniversity.builder()
                .id(entity.getId())
                .name(entity.getName())
                .fieldOfStudies(entity.getFieldOfStudies());
        return responseUniversity.build();
    }

    @Override
    public void updateFromDto(RequestUniversity req, University entity) {
        if (req == null) {
            return;
        }

        if (req.getName() != null) {
            entity.setName(req.getName());
        }
    }

    @Override
    public List<University> toEntity(List<RequestUniversity> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<University> list = new ArrayList<University>(dtoList.size());
        for (RequestUniversity requestUniversity : dtoList) {
            list.add(toEntity(requestUniversity));
        }

        return list;
    }

    @Override
    public List<ResponseUniversity> toDto(List<University> entityList) {
        if (entityList == null) {
            return null;
        }

        List<ResponseUniversity> list = new ArrayList<ResponseUniversity>(entityList.size());
        for (University university : entityList) {
            list.add(toDto(university));
        }

        return list;
    }

    @Override
    public Set<University> toEntity(Set<RequestUniversity> rqSet) {
        if (rqSet == null) {
            return null;
        }

        Set<University> set = new LinkedHashSet<University>(Math.max((int) (rqSet.size() / .75f) + 1, 16));
        for (RequestUniversity requestUniversity : rqSet) {
            set.add(toEntity(requestUniversity));
        }

        return set;
    }

    @Override
    public Set<ResponseUniversity> toDto(Set<University> eSet) {
        if (eSet == null) {
            return null;
        }

        Set<ResponseUniversity> set = new LinkedHashSet<ResponseUniversity>(Math.max((int) (eSet.size() / .75f) + 1, 16));
        for (University university : eSet) {
            set.add(toDto(university));
        }

        return set;
    }
}
