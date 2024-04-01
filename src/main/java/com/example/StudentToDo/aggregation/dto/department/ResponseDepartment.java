package com.example.StudentToDo.aggregation.dto.department;

import com.example.StudentToDo.aggregation.entity.University;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResponseDepartment {
    private Long id;
    private String universityId;
    private String name;
    private University university;
}
