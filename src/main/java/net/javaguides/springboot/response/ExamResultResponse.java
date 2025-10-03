package net.javaguides.springboot.response;

import lombok.Data;

import java.util.List;

public class ExamResultResponse extends BaseResponse<List<ExamResultResponse.ExamResultData>>{

    @Data
    public static class ExamResultData {
        private Long id;
        private ExamResponse.ExamData exam;
        private int userId;
        private int numberCorrectAnswers;
        private int totalQuestions;
        private String submittedAt;
    }
}
