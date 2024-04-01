package com.example.StudentToDo.aggregation.dto.student;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -8223050152551494099L;
    private String message;
    private LocalDateTime time;
}
