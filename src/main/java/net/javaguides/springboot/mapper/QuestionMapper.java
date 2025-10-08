package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.QuestionRequest;
import net.javaguides.springboot.entity.Question;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.response.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question toEntity(QuestionRequest request);

    @Mapping(source = "question.id", target = "id")
    @Mapping(source = "subject", target = "subject")
    QuestionResponse.QuestionData toQuestionData(Question question, Subject subject);
}
