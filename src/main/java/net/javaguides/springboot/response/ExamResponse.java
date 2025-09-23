package net.javaguides.springboot.response;

import lombok.Getter;
import lombok.Setter;
import net.javaguides.springboot.entity.Subject;

import java.util.List;

public class ExamResponse extends BaseResponse<List<ExamResponse.ExamData>> {

    @Getter
    @Setter
    public static class ExamData {
        private Long id;

        private Subject subject;
        private String title;
        private int duration_seconds;
        private List<QuestionResponse.QuestionData> questions;
        private String created_at;
    }
}
