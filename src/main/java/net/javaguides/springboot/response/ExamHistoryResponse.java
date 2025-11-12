package net.javaguides.springboot.response;

import lombok.Data;
import net.javaguides.springboot.entity.Subject;

import java.util.List;

public class ExamHistoryResponse extends BaseResponse<ExamHistoryResponse.ExamResultOfAnswer> {

    @Data
    public static class ExamResultOfAnswer {
        private ExamHistoryData examResult;
    }

    @Data
    public static class ExamHistoryData {
        private Long id;

        private Subject subject;
        private String title;
        private int durationSeconds;
        private List<QuestionHistoryData> questions;
        private String createdAt;
    }

    @Data
    public static class QuestionHistoryData {
        private Long id;
        private Subject subject;
        private String content;
        private String contentImage;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private int correctAnswer;
        private String explanation;
        private String explanationImage;
        private int answer;
    }

    @Data
    public static class ExamAnswerData {
        private int questionId;
        private int chosenAnswer;
    }
}
