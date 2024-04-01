package com.example.StudentToDo.aggregation.mapper;

import com.example.StudentToDo.aggregation.entity.Department;
import com.example.StudentToDo.aggregation.mapper.interfaces.DepartmentMapperInterface;
import com.example.StudentToDo.aggregation.dto.department.RequestDepartment;
import com.example.StudentToDo.aggregation.dto.department.ResponseDepartment;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Component
@Primary
public class DepartmentMapper implements DepartmentMapperInterface {
    @Override
    public Department toEntity(RequestDepartment req) {
        if ( req == null ) {
            return null;
        }

        Department.DepartmentBuilder department = Department.builder();

        department.university( req.getUniversity() );

        return department.build();
    }

    @Override
    public ResponseDepartment toDto(Department entity) {
        if ( entity == null ) {
            return null;
        }

        ResponseDepartment responseDepartment = new ResponseDepartment();

        responseDepartment.setId( entity.getId() );
        responseDepartment.setName( entity.getName() );
        responseDepartment.setUniversity( entity.getUniversity() );

        return responseDepartment;
    }

    @Override
    public void updateFromDto(RequestDepartment req, Department entity) {
        if ( req == null ) {
            return;
        }

        if ( req.getUniversity() != null ) {
            entity.setUniversity( req.getUniversity() );
        }
    }

    @Override
    public List<Department> toEntity(List<RequestDepartment> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Department> list = new ArrayList<Department>( dtoList.size() );
        for ( RequestDepartment requestDepartment : dtoList ) {
            list.add( toEntity( requestDepartment ) );
        }

        return list;
    }

    @Override
    public List<ResponseDepartment> toDto(List<Department> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ResponseDepartment> list = new ArrayList<ResponseDepartment>( entityList.size() );
        for ( Department department : entityList ) {
            list.add( toDto( department ) );
        }

        return list;
    }

    @Override
    public Set<Department> toEntity(Set<RequestDepartment> rqSet) {
        if ( rqSet == null ) {
            return null;
        }

        Set<Department> set = new LinkedHashSet<Department>( Math.max( (int) ( rqSet.size() / .75f ) + 1, 16 ) );
        for ( RequestDepartment requestDepartment : rqSet ) {
            set.add( toEntity( requestDepartment ) );
        }

        return set;
    }

    @Override
    public Set<ResponseDepartment> toDto(Set<Department> eSet) {
        if ( eSet == null ) {
            return null;
        }

        Set<ResponseDepartment> set = new LinkedHashSet<ResponseDepartment>( Math.max( (int) ( eSet.size() / .75f ) + 1, 16 ) );
        for ( Department department : eSet ) {
            set.add( toDto( department ) );
        }

        return set;
    }
}
