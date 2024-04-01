package com.example.StudentToDo.aggregation.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class StudentImage  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String filePath;
    private String originalName;
}
