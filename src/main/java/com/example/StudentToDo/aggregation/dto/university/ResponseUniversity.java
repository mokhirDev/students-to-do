package com.example.StudentToDo.aggregation.dto.university;

import com.example.StudentToDo.aggregation.entity.Department;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ResponseUniversity implements Serializable {
    @Serial
    private static final long serialVersionUID = -6009592180069656694L;
    private Long id;
    private String name;
    private Set<Department> fieldOfStudies;
}
