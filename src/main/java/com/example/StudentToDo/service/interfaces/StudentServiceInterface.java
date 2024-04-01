package com.example.StudentToDo.service.interfaces;

import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.aggregation.dto.student.ResponseStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface StudentServiceInterface {
    ResponseStudent registerStudent(RequestStudent student);
    ResponseStudent findStudentById(String id);
    List<ResponseStudent> findStudentByName(String name);
    ResponseMessage remove(String studentId);
    Page<ResponseStudent> findAllStudents(Pageable pageable);
    ResponseStudent modify(RequestStudent updateStd);
    byte[] getAllStudentExcel() throws IOException;
    byte[] getStudentCVPdf(String userId) throws IOException;
    ResponseStudent setDepartment(RequestStudent requestStudent);
}
