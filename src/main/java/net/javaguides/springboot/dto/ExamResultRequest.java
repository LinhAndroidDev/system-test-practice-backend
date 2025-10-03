package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class ExamResultRequest {
    private Long id;

    private int examId;
    private int userId;
    private int numberCorrectAnswers;
    private int totalQuestions;
    private String submittedAt;
}
