package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class QuestionRequest {
    private Long id;
    private int subjectId;
    private String content;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int correctAnswer;
    private String explanation;
}
