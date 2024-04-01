package com.example.StudentToDo.aggregation.dto.department;

import com.example.StudentToDo.aggregation.entity.Department;
import com.example.StudentToDo.aggregation.entity.University;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RequestDepartment implements Serializable {
    @Serial
    private static final long serialVersionUID = 2711630152620137824L;
    private Department department;
    private University university;
}
