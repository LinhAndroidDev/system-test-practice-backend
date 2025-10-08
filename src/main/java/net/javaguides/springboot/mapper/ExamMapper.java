package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.ExamRequest;
import net.javaguides.springboot.entity.Exam;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamMapper {
    @Mapping(target = "createdAt", ignore = true)
    Exam toEntity(ExamRequest request);

    @Mapping(source = "exam.id", target = "id")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "questions", target = "questions")
    @Mapping(source = "exam.createdAt", target = "createdAt", qualifiedByName = "dateToString")
    ExamResponse.ExamData toExamData(Exam exam, Subject subject, List<QuestionResponse.QuestionData> questions);

    @Named("dateToString")
    default String dateToString(LocalDateTime date) {
        return date == null ? null : date.toString();
    }
}
