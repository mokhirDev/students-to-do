package com.example.StudentToDo.service;

import com.example.StudentToDo.aggregation.entity.Department;
import com.example.StudentToDo.aggregation.entity.Student;
import com.example.StudentToDo.exceptions.DatabaseException;
import com.example.StudentToDo.exceptions.NoCreatedEntityYetException;
import com.example.StudentToDo.exceptions.NotFoundException;
import com.example.StudentToDo.aggregation.mapper.interfaces.StudentMapperInterface;
import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.student.ResponseStudent;
import com.example.StudentToDo.repository.DepartmentRepository;
import com.example.StudentToDo.repository.StudentRepository;
import com.example.StudentToDo.service.interfaces.StudentServiceInterface;
import com.example.StudentToDo.service.convert.DataToExcel;
import com.example.StudentToDo.service.convert.DataToPdf;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StudentService implements StudentServiceInterface {
    private final DataToPdf dataToPdf;
    private final DataToExcel dataToExcel;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentMapperInterface mapper;

    @Override
    public ResponseStudent registerStudent(RequestStudent student) {
        List<Department> all = departmentRepository.findAll();
        if (all.isEmpty()) {
            log.error("Any departments didn't created yet");
            throw new NoCreatedEntityYetException("Any departments didn't created yet");
        }
        Student savedStudent = studentRepository.save(mapper.toEntity(student));
        log.info(savedStudent+": successfully created");
        return mapper.toDto(savedStudent);
    }

    @Override
    public ResponseStudent findStudentById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(Long.valueOf(id));
            if (student.isPresent()) {
                log.info(student.get()+": is present");
                return mapper.toDto(student.get());
            } else {
                log.warn(id + ": doesn't exist");
                throw new NotFoundException(id + ": doesn't exist");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        } catch (Exception exception) {
            throw new DatabaseException(exception.getMessage());
        }
    }

    @Override
    public List<ResponseStudent> findStudentByName(String name) {
        log.info(name+" is exist");
        return studentRepository.findAllByFirstName(name)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseStudent modify(RequestStudent updateStd) {
        try {
            Optional<Student> byId = studentRepository.findById(updateStd.getStudent().getId());
            if (byId.isPresent()) {
                log.info(byId.get()+": is present");
                mapper.updateFromDto(updateStd, byId.get());
                Student saved = studentRepository.save(byId.get());
                log.info(byId.get()+": is updated");
                return mapper.toDto(saved);
            }
            log.error(updateStd.getStudent().getId() + ": doesn't exist");
            throw new NotFoundException(updateStd.getStudent().getId() + ": doesn't exist");
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }


    @Override
    public ResponseMessage remove(String studentId) {
        Long id = Long.valueOf(studentId);
        try {
            Optional<Student> byId = studentRepository.findById(id);
            if (byId.isEmpty()) {
                log.warn(byId.get().getId() + ": doesn't exist");
                throw new NotFoundException(byId.get().getId() + ": doesn't exist");
            }
            log.info(byId.get()+": is deleted");
            studentRepository.deleteById(id);
            return
                    ResponseMessage
                            .builder()
                            .message("Deleted successfully, student with id: %d".formatted(id))
                            .time(LocalDateTime.now())
                            .build();
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public Page<ResponseStudent> findAllStudents(Pageable pageable) {
        try {
            Page<Student> allStudents = studentRepository.findAll(pageable);
            if (allStudents.isEmpty()) {
                throw new NotFoundException("Topilmadi!");
            }
            return allStudents.map(mapper::toDto);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public byte[] getAllStudentExcel() throws IOException {
        List<Student> studentList = studentRepository.findAll();
        log.info("All students found: "+studentList);
        return dataToExcel.createExcelFile(studentList);
    }

    @Override
    public byte[] getStudentCVPdf(String userId) throws IOException {
        try {
            Optional<Student> byId = studentRepository.findById(Long.valueOf(userId));
            if (byId.isEmpty()) {
                log.warn(byId.get().getId() + ": doesn't exist");
                throw new NotFoundException(byId.get().getId() + ": doesn't exist");
            }
            log.info("student pdf cv is created");
            return dataToPdf.insertTextToPdf(byId.get());
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

    @Override
    public ResponseStudent setDepartment(RequestStudent requestStudent) {
        try {
            Department department = requestStudent.getStudent().getDepartment();
            Long stdId = requestStudent.getStudent().getId();
            Optional<Student> byId = studentRepository.findById(stdId);
            if (byId.isEmpty()) {
                log.warn(byId.get().getId() + ": doesn't exist");
                throw new NotFoundException(byId.get().getId() + ": doesn't exist");
            }
            Student student = byId.get();
            student.setDepartment(department);
            studentRepository.save(student);
            log.info("department set to student: "+student);
            return mapper.toDto(student);
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (Exception ex) {
            throw new DatabaseException(ex.getMessage());
        }
    }

}
