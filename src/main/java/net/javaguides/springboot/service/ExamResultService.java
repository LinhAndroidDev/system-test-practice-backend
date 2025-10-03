package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.ExamResultRequest;
import net.javaguides.springboot.entity.Exam;
import net.javaguides.springboot.entity.ExamResult;
import net.javaguides.springboot.entity.Question;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.mapper.ExamMapper;
import net.javaguides.springboot.mapper.ExamResultMapper;
import net.javaguides.springboot.mapper.QuestionMapper;
import net.javaguides.springboot.repository.ExamRepository;
import net.javaguides.springboot.repository.ExamResultRepository;
import net.javaguides.springboot.repository.QuestionRepository;
import net.javaguides.springboot.repository.SubjectRepository;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.ExamResultResponse;
import net.javaguides.springboot.response.QuestionResponse;
import net.javaguides.springboot.utils.Constants;
import net.javaguides.springboot.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    @Autowired
    ExamResultRepository examResultRepository;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    QuestionRepository questionRepository;

    private final ExamResultMapper examResultMapper;

    public List<ExamResultResponse.ExamResultData> getAllExamResults() {
        return examResultRepository.findAll().stream().map(examResult -> {
            Exam exam = examRepository.findById((long) examResult.getExamId()).orElse(new Exam());
            ExamResponse.ExamData examData = new ExamResponse.ExamData();
            Subject subjectExam = subjectRepository.findById((long) exam.getSubject_id()).orElse(new Subject());
            List<Integer> idQuestions = ConvertUtils.convertStringToListNumber(exam.getQuestions());
            List<QuestionResponse.QuestionData> questionData = idQuestions.stream().map(id -> {
                Question question = questionRepository.findById((long) id).orElse(new Question());
                Subject subjectQuestion = subjectRepository.findById((long) exam.getSubject_id()).orElse(new Subject());
                return QuestionMapper.toQuestionData(question, subjectQuestion);
            }).toList();
            ExamMapper.toExamData(exam, subjectExam, questionData);

            return examResultMapper.toExamResultData(examResult, examData);
        }).toList();
    }

    public void addExamResult(ExamResultRequest request) {
        ExamResult examResult = examResultMapper.toEntity(request);
        examResultRepository.save(examResult);
    }

    public int deleteExamResult(Long id) {
        if (examResultRepository.findById(id).isEmpty()) {
            return Constants.FAILURE;
        }
        examResultRepository.deleteById(id);
        return Constants.SUCCESS;
    }

    public int updateExamResult(ExamResultRequest request) {
        return examResultRepository.findById(request.getId()).map(examResult -> {
            ExamResult updatedExamResult = examResultMapper.toEntity(request);
            examResultRepository.save(updatedExamResult);
            return Constants.SUCCESS;
        }).orElse(Constants.FAILURE);
    }
}
