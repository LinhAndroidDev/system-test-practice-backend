package net.javaguides.springboot.response;

import lombok.Data;

import java.util.List;

public class ExamAnswerResponse extends BaseResponse<ExamAnswerResponse.ExamResultOfAnswer> {

    @Data
    public static class ExamResultOfAnswer {
        private ExamResultResponse.ExamResultData examResult;
        private List<ExamAnswerData> examAnswers;
    }

    @Data
    public static class ExamAnswerData {
        private QuestionResponse.QuestionData question;
        private int chosenAnswer;
    }
}
