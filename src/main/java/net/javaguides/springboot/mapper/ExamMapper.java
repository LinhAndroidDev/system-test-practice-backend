package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.ExamRequest;
import net.javaguides.springboot.entity.Exam;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.QuestionResponse;

import java.util.List;

public class ExamMapper {
    public static void toEntity(ExamRequest request, Exam exam) {
        exam.setId(request.getId());
        exam.setSubject_id(request.getSubject_id());
        exam.setTitle(request.getTitle());
        exam.setDuration_seconds(request.getDuration_seconds());
        exam.setQuestions(request.getQuestions());
    }

    public static ExamResponse.ExamData toExamData(Exam exam, Subject subject, List<QuestionResponse.QuestionData> questions) {
        ExamResponse.ExamData examData = new ExamResponse.ExamData();
        examData.setId(exam.getId());
        examData.setSubject(subject);
        examData.setTitle(exam.getTitle());
        examData.setDuration_seconds(exam.getDuration_seconds());
        examData.setQuestions(questions);
        examData.setCreated_at(exam.getCreatedAt().toString());
        return examData;
    }
}
