package com.example.StudentToDo.aggregation.dto.student;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseStudent implements Serializable {
    @Serial
    private static final long serialVersionUID = 2559040530163258393L;
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String imageName;
}
