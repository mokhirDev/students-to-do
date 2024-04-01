package com.example.StudentToDo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
public class StudentToDoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentToDoApplication.class, args);
    }
}