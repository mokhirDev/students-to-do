package com.example.StudentToDo.aggregation.dto.student;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class StdImgResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -5496090954652133987L;
    private String type;
    private String filePath;
    private String originalName;
}
