package net.javaguides.springboot.mapper;

import net.javaguides.springboot.dto.ExamResultRequest;
import net.javaguides.springboot.entity.ExamResult;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.ExamResultResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ExamResultMapper {
    @Mapping(source = "submittedAt", target = "submittedAt", qualifiedByName = "stringToDate")
    ExamResult toEntity(ExamResultRequest request);

    @Mapping(source = "examData", target = "exam")
    @Mapping(source = "examResult.id", target = "id")
    @Mapping(source = "examResult.submittedAt", target = "submittedAt", qualifiedByName = "dateToString")
    ExamResultResponse.ExamResultData toExamResultData(
            ExamResult examResult, ExamResponse.ExamData examData
    );

    @Named("stringToDate")
    default LocalDateTime stringToDate(String date) {
        return date == null ? null : LocalDateTime.parse(date);
    }

    @Named("dateToString")
    default String dateToString(LocalDateTime date) {
        return date == null ? null : date.toString();
    }
}
