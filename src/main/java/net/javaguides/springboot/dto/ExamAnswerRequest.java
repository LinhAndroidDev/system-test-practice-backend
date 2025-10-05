package net.javaguides.springboot.dto;

import lombok.Data;

@Data
public class ExamAnswerRequest {
    private int questionId;
    private int chosenAnswer;
}
