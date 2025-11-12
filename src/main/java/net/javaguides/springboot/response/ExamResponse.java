package net.javaguides.springboot.response;

import lombok.Data;
import net.javaguides.springboot.entity.Subject;

import java.util.List;

public class ExamResponse extends BaseResponse<List<ExamResponse.ExamData>> {

    @Data
    public static class ExamData {
        private Long id;

        private Subject subject;
        private String title;
        private int durationSeconds;
        private List<QuestionResponse.QuestionData> questions;
        private String createdAt;

        public ExamHistoryResponse.ExamHistoryData toExamHistoryData(List<ExamHistoryResponse.ExamAnswerData> examAnswerData) {
            ExamHistoryResponse.ExamHistoryData examHistoryData = new ExamHistoryResponse.ExamHistoryData();
            examHistoryData.setId(this.id);
            examHistoryData.setSubject(this.subject);
            examHistoryData.setTitle(this.title);
            examHistoryData.setDurationSeconds(this.durationSeconds);
            examHistoryData.setCreatedAt(this.createdAt);
            List<ExamHistoryResponse.QuestionHistoryData> questionHistories = this.questions.stream().map(question -> {
                ExamHistoryResponse.ExamAnswerData answer = examAnswerData.stream().filter(e -> e.getQuestionId() == question.getId())
                        .findFirst()
                        .orElse(null);
                int chosenAnswer = answer != null ? answer.getChosenAnswer() : -1;
                ExamHistoryResponse.QuestionHistoryData questionHistoryData = new ExamHistoryResponse.QuestionHistoryData();
                questionHistoryData.setId(question.getId());
                questionHistoryData.setSubject(question.getSubject());
                questionHistoryData.setContent(question.getContent());
                questionHistoryData.setContentImage(question.getContentImage());
                questionHistoryData.setOptionA(question.getOptionA());
                questionHistoryData.setOptionB(question.getOptionB());
                questionHistoryData.setOptionC(question.getOptionC());
                questionHistoryData.setOptionD(question.getOptionD());
                questionHistoryData.setCorrectAnswer(question.getCorrectAnswer());
                questionHistoryData.setExplanation(question.getExplanation());
                questionHistoryData.setExplanationImage(question.getExplanationImage());
                questionHistoryData.setAnswer(chosenAnswer);
                return questionHistoryData;
            }).toList();
            examHistoryData.setQuestions(questionHistories);
            return examHistoryData;
        }
    }
}
