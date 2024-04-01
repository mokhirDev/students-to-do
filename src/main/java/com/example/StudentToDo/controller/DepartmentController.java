package com.example.StudentToDo.controller;

import com.example.StudentToDo.aggregation.dto.department.RequestDepartment;
import com.example.StudentToDo.aggregation.dto.department.ResponseDepartment;
import com.example.StudentToDo.aggregation.dto.student.ResponseMessage;
import com.example.StudentToDo.service.interfaces.DepartmentServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.StudentToDo.utils.ApiUrls.*;

@RestController
@RequestMapping(DEPARTMENT_MAIN_URL)
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentServiceInterface service;

    @PostMapping
    public ResponseEntity<ResponseDepartment> add(@RequestBody RequestDepartment department) {
        return new ResponseEntity<>(service.add(department), HttpStatus.OK);
    }

    @GetMapping(GET_BY_ID)
    public ResponseEntity<ResponseDepartment> findById(@PathVariable String entityId) {
        return new ResponseEntity<>(service.findById(entityId), HttpStatus.OK);
    }

    @GetMapping(GET_BY_NAME)
    public List<ResponseDepartment> findByName(@PathVariable String entityName) {
        return service.findByName(entityName);
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<ResponseMessage> removeById(@PathVariable String entityId) {
        return new ResponseEntity<>(service.removeById(entityId), HttpStatus.OK);
    }

    @GetMapping(ALL)
    public Page<ResponseDepartment> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PutMapping
    public ResponseEntity<ResponseDepartment> update(@RequestBody RequestDepartment updateDep) {
        return new ResponseEntity<>(service.update(updateDep), HttpStatus.OK);
    }
}
