package com.example.StudentToDo.aggregation.dto.student;

import com.example.StudentToDo.aggregation.entity.Student;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestStudent implements Serializable {
    @Serial
    private static final long serialVersionUID = -4111686401163460729L;
    private Student student;
    private String base64File;
}
