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
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private int correctAnswer;
        private String explanation;
    }
}
