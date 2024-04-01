package com.example.StudentToDo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> on(NotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),
                "Bunday malumot topilmadi",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ErrorResponse> on(DatabaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Bazada nimadir xatolik mavjud",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(NoCreatedEntityYetException.class)
    public ResponseEntity<ErrorResponse> on(NoCreatedEntityYetException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NO_CONTENT.value(),
                "Didn't created:",
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse);
    }
}
