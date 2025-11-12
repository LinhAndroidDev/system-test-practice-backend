package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.ExamAnswerRequest;
import net.javaguides.springboot.entity.ExamAnswer;
import net.javaguides.springboot.response.ExamHistoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamAnswerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "resultId", target = "resultId")
    ExamAnswer toEntity(ExamAnswerRequest request, Long resultId);

    ExamHistoryResponse.ExamAnswerData toExamAnswerData(ExamAnswer examAnswer);
}
