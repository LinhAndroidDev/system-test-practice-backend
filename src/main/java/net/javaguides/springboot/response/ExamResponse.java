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
    }
}
