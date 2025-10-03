package net.javaguides.springboot.response;

import lombok.Data;
import net.javaguides.springboot.entity.Subject;

import java.util.List;

public class QuestionResponse extends BaseResponse<List<QuestionResponse.QuestionData>> {

    @Data
    public static class QuestionData {
        private Long id;
        private Subject subject;
        private String content;
        private String option_a;
        private String option_b;
        private String option_c;
        private String option_d;
        private int correct_answer;
        private String explanation;
    }
}
