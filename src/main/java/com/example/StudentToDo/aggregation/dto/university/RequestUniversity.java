package com.example.StudentToDo.aggregation.dto.university;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RequestUniversity implements Serializable {
    @Serial
    private static final long serialVersionUID = 492438379847610177L;
    private String name;
}
