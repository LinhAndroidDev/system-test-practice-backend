package net.javaguides.springboot.service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dto.DeleteExamAnswerRequest;
import net.javaguides.springboot.dto.ExamAnswerRequest;
import net.javaguides.springboot.dto.ExamResultRequest;
import net.javaguides.springboot.entity.*;
import net.javaguides.springboot.mapper.ExamAnswerMapper;
import net.javaguides.springboot.mapper.ExamMapper;
import net.javaguides.springboot.mapper.ExamResultMapper;
import net.javaguides.springboot.repository.*;
import net.javaguides.springboot.response.ExamAnswerResponse;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.ExamResultResponse;
import net.javaguides.springboot.response.QuestionResponse;
import net.javaguides.springboot.utils.Constants;
import net.javaguides.springboot.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    ExamAnswerRepository examAnswerRepository;

    @Autowired
    ExamService examService;

    private final ExamResultMapper examResultMapper;

    private final ExamAnswerMapper examAnswerMapper;

    private final ExamMapper examMapper;

    public List<ExamResultResponse.ExamResultData> getAllExamResults() {
        return examResultRepository.findAll().stream().map(examResult -> {
            Exam exam = examRepository.findById((long) examResult.getExamId()).orElse(new Exam());
            Subject subjectExam = subjectRepository.findById((long) exam.getSubjectId()).orElse(new Subject());
            List<Integer> idQuestions = ConvertUtils.convertStringToListNumber(exam.getQuestions());
            List<QuestionResponse.QuestionData> questionData = examService.getAllQuestionsByIds(idQuestions, subjectExam.getId());
            ExamResponse.ExamData examData = examMapper.toExamData(exam, subjectExam, questionData);

            return examResultMapper.toExamResultData(examResult, examData);
        }).toList();
    }

    public void addExamResult(ExamResultRequest request) {
        ExamResult examResult = examResultMapper.toEntity(request);
        examResultRepository.save(examResult);
        Long resultId = examResult.getId();
        addAllExamAnswers(request.getExamAnswers(), resultId);
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

    private void addAllExamAnswers(List<ExamAnswerRequest> requests, Long resultId) {
        List<ExamAnswer> answers = requests.stream()
                .map(examAnswerRequest -> examAnswerMapper.toEntity(examAnswerRequest, resultId))
                .toList();
        examAnswerRepository.saveAll(answers);
    }

    public ExamAnswerResponse.ExamResultOfAnswer getAllAnswersByExamResultId(int resultId) {
        ExamResult examResult = examResultRepository.findById((long) resultId).orElse(new ExamResult());
        List<ExamAnswerResponse.ExamAnswerData> examAnswersData =
                examAnswerRepository.findByResultId(resultId).stream()
                        .flatMap(examAnswers -> examAnswers.stream()
                                .map(examAnswerMapper::toExamAnswerData))
                        .toList();
        ExamAnswerResponse.ExamResultOfAnswer examResultOfAnswer = new ExamAnswerResponse.ExamResultOfAnswer();
        ExamResponse.ExamData examData = examService.getExamById(examResult.getExamId());
        examResultOfAnswer.setExamResult(examResultMapper.toExamResultData(examResult, examData));
        examResultOfAnswer.setExamAnswers(examAnswersData);
        return examResultOfAnswer;
    }

    public void deleteExamAnswerByResultId(DeleteExamAnswerRequest request) {
        List<Long> idExamAnswers = ConvertUtils.convertStringToListNumber(request.getIdExamAnswers())
                .stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());
        examAnswerRepository.deleteAllById(idExamAnswers);
    }
}
