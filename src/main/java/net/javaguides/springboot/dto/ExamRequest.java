package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class ExamRequest {
    private Long id;

    private int subject_id;
    private String title;
    private int duration_seconds;
    private String questions;
}
