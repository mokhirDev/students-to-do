package com.example.StudentToDo.service.interfaces;

import com.example.StudentToDo.aggregation.dto.student.RequestStudent;
import com.example.StudentToDo.aggregation.dto.student.StdImgResponse;

import java.io.IOException;

public interface ImageServiceInterface {
    StdImgResponse uploadImage(RequestStudent student) throws Exception;
    String downloadImage(Long id) throws IOException;
}
