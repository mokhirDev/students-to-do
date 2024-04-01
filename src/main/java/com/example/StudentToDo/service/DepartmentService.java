package com.example.StudentToDo.service;
import com.example.StudentToDo.aggregation.entity.Department;
import com.example.StudentToDo.aggregation.entity.University;
import com.example.StudentToDo.exceptions.DatabaseException;
import com.example.StudentToDo.exceptions.NoCreatedEntityYetException;
import com.example.StudentToDo.exceptions.NotFoundException;
import com.example.StudentToDo.aggregation.mapper.interfaces.DepartmentMapperInterface;
import com.example.StudentToDo.aggregation.dto.department.RequestDepartment;
import com.example.StudentToDo.aggregation.dto.department.ResponseDepartment;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.repository.DepartmentRepository;
import com.example.StudentToDo.repository.UniversityRepository;
import com.example.StudentToDo.service.interfaces.DepartmentServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class DepartmentService implements DepartmentServiceInterface {
    private final DepartmentRepository repository;
    private final UniversityRepository universityRepository;
    private final DepartmentMapperInterface mapper;

    @Override
    public ResponseDepartment add(RequestDepartment request) {
        try {
            List<University> all = universityRepository.findAll();
            if (all.isEmpty()) {
                log.warn("Any universities didn't created yet");
                throw new NoCreatedEntityYetException("Any universities didn't created yet");
            }
            Department saved = repository.save(

                    Department.builder()
                            .name(request.getDepartment().getName())
                            .university(request.getUniversity())
                            .build()
            );
            log.info("Department saved: "+saved);
            return mapper.toDto(saved);
        }catch (NoCreatedEntityYetException ex){
            throw new NoCreatedEntityYetException(ex.getMessage());
        }catch (Exception ex){
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public ResponseDepartment findById(String id) {
        try {
            Optional<Department> byId = repository.findById(Long.valueOf(id));
            if (byId.isEmpty()){
                log.warn(id+": doesn't exist");
                throw new NotFoundException(id+": doesn't exist");
            }
            log.info(id+": is present");
            return byId.map(mapper::toDto).orElse(null);
        }catch (NotFoundException ex){
            throw new NotFoundException(ex.getMessage());
        }catch (Exception ex){
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public List<ResponseDepartment> findByName(String name) {
        List<Department> firstByName = repository.findFirstByName(name);
       if (firstByName.isEmpty()){
           log.warn(name+": This name doesn't exist");
           throw new NotFoundException(name+": This name doesn't exist");
       }
        List<ResponseDepartment> collect = firstByName.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
       log.info("Found by name are: "+collect);
       return collect;
    }

    @Override
    public ResponseMessage removeById(String departmentId) {
        repository.deleteById(Long.valueOf(departmentId));
        log.warn(departmentId+": deleted");
        return new ResponseMessage("deleted!", LocalDateTime.now());
    }

    @Override
    public Page<ResponseDepartment> findAll(Pageable pageable) {
        Page<Department> all = repository.findAll(pageable);
        log.info("Found all department: "+all);
        return all.map(mapper::toDto);
    }

    @Override
    public ResponseDepartment update(RequestDepartment updateDep) {
     try {
         Optional<Department> byId = repository.findById(updateDep.getDepartment().getId());
         if (byId.isEmpty()){
             log.warn(byId.get()+": doesn't exist");
             throw new NotFoundException(byId.get()+": doesn't exist");
         }
         Department department = byId.get();
         log.info(department+": is exist");
         department.setName(updateDep.getDepartment().getName());
         if (department.getUniversity() != null) {
             department.setUniversity(updateDep.getUniversity());
         }
         Department saved = repository.save(department);
         log.info(saved+": saved");
         return mapper.toDto(saved);
     }catch (NotFoundException ex){
         throw new NotFoundException(ex.getMessage());
     }catch (Exception ex){
         throw new DatabaseException(ex.getMessage());
     }
    }
}
