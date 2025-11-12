package net.javaguides.springboot.response;

import lombok.Data;

public class ExamResultResponse extends BaseResponse<ExamResultResponse.ExamResultData> {
    
    @Data
    public static class ExamResultData {
        private Long id;
        private ExamResponse.ExamData exam;
        private int userId;
        private int numberCorrectAnswers;
        private int numberWrongAnswers;
        private int totalQuestions;
        private String submittedAt;
    }
}
