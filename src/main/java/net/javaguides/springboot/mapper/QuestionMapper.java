package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.QuestionRequest;
import net.javaguides.springboot.entity.Question;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.response.QuestionResponse;

public class QuestionMapper {
    public static void toEntity(QuestionRequest request, Question question) {
        question.setSubject_id(request.getSubject_id());
        question.setContent(request.getContent());
        question.setOption_a(request.getOption_a());
        question.setOption_b(request.getOption_b());
        question.setOption_c(request.getOption_c());
        question.setOption_d(request.getOption_d());
        question.setCorrect_answer(request.getCorrect_answer());
    }

    public static QuestionResponse.QuestionData toQuestionData(Question question, Subject subject) {
        QuestionResponse.QuestionData data = new QuestionResponse.QuestionData();
        data.setId(question.getId());
        data.setSubject(subject);
        data.setContent(question.getContent());
        data.setOption_a(question.getOption_a());
        data.setOption_b(question.getOption_b());
        data.setOption_c(question.getOption_c());
        data.setOption_d(question.getOption_d());
        data.setCorrect_answer(question.getCorrect_answer());
        return data;
    }
}
