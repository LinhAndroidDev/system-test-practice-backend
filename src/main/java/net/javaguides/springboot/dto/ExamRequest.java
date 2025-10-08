package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class ExamRequest {
    private Long id;

    private int subjectId;
    private String title;
    private int durationSeconds;
    private String questions;
}
