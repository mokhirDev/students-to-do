package com.example.StudentToDo.aggregation.mapper;

import com.example.StudentToDo.aggregation.entity.Student;
import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.ResponseStudent;
import com.example.StudentToDo.aggregation.mapper.interfaces.StudentMapperInterface;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Component
@Primary
public class StudentMapper implements StudentMapperInterface {
    @Override
    public Student toEntity(RequestStudent req) {
        if ( req == null ) {
            return null;
        }
        Student reqStudent = req.getStudent();
        Student.StudentBuilder student = Student
                .builder()
                .id(reqStudent.getId())
                .firstName(reqStudent.getFirstName())
                .middleName(reqStudent.getMiddleName())
                .lastName(reqStudent.getLastName())
                .department(reqStudent.getDepartment());
        return student.build();
    }

    @Override
    public ResponseStudent toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        ResponseStudent responseStudent = new ResponseStudent();
        responseStudent.setId( entity.getId() );
        responseStudent.setFirstName( entity.getFirstName() );
        responseStudent.setMiddleName( entity.getMiddleName() );
        responseStudent.setLastName( entity.getLastName() );
        responseStudent.setImageName( entity.getImageName() );

        return responseStudent;
    }

    @Override
    public void updateFromDto(RequestStudent req, Student entity) {
        if ( req == null ) {
            return;
        }
    }

    @Override
    public List<Student> toEntity(List<RequestStudent> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( dtoList.size() );
        for ( RequestStudent requestStudent : dtoList ) {
            list.add( toEntity( requestStudent ) );
        }

        return list;
    }

    @Override
    public List<ResponseStudent> toDto(List<Student> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ResponseStudent> list = new ArrayList<ResponseStudent>( entityList.size() );
        for ( Student student : entityList ) {
            list.add( toDto( student ) );
        }

        return list;
    }

    @Override
    public Set<Student> toEntity(Set<RequestStudent> rqSet) {
        if ( rqSet == null ) {
            return null;
        }

        Set<Student> set = new LinkedHashSet<Student>( Math.max( (int) ( rqSet.size() / .75f ) + 1, 16 ) );
        for ( RequestStudent requestStudent : rqSet ) {
            set.add( toEntity( requestStudent ) );
        }

        return set;
    }

    @Override
    public Set<ResponseStudent> toDto(Set<Student> eSet) {
        if ( eSet == null ) {
            return null;
        }

        Set<ResponseStudent> set = new LinkedHashSet<ResponseStudent>
                ( Math.max( (int) ( eSet.size() / .75f ) + 1, 16 ) );
        for ( Student student : eSet ) {
            set.add( toDto( student ) );
        }

        return set;
    }
}
