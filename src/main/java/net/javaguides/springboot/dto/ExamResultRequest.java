package net.javaguides.springboot.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamResultRequest {
    private Long id;

    private int examId;
    private int userId;
    private int numberCorrectAnswers;
    private int totalQuestions;
    private List<ExamAnswerRequest> examAnswers;
}
