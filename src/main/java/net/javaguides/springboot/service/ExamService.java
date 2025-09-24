package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.ExamRequest;
import net.javaguides.springboot.dto.QuestionRequest;
import net.javaguides.springboot.dto.SubjectRequest;
import net.javaguides.springboot.entity.Exam;
import net.javaguides.springboot.entity.Question;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.mapper.ExamMapper;
import net.javaguides.springboot.mapper.QuestionMapper;
import net.javaguides.springboot.repository.ExamRepository;
import net.javaguides.springboot.repository.QuestionRepository;
import net.javaguides.springboot.repository.SubjectRepository;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.response.QuestionResponse;
import net.javaguides.springboot.utils.Constants;
import net.javaguides.springboot.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ExamRepository examRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public int addSubject(String nameSubject) {
        if (subjectRepository.findByNameSubject(nameSubject).isPresent()) {
            return Constants.DATA_CONFLICT;
        }

        Subject subject = new Subject();
        subject.setNameSubject(nameSubject);
        subjectRepository.save(subject);
        return Constants.SUCCESS;
    }

    public int deleteSubject(Long id) {
        if (subjectRepository.findById(id).isPresent()) {
            subjectRepository.deleteById(id);
            return Constants.SUCCESS;
        }

        return Constants.FAILURE;
    }

    public int updateSubject(SubjectRequest subjectRequest) {
        return subjectRepository.findById(subjectRequest.getId())
                .map(subject -> {
                    subject.setNameSubject(subjectRequest.getName_subject());
                    subjectRepository.save(subject);
                    return Constants.SUCCESS;
                })
                .orElse(Constants.FAILURE);
    }

    public List<QuestionResponse.QuestionData> getAllQuestions() {
        return questionRepository.findAll().stream().map(question -> {
            Subject subject = subjectRepository.findById((long) question.getSubject_id()).orElse(new Subject());
            return QuestionMapper.toQuestionData(question, subject);
        }).collect(Collectors.toList());
    }

    public int addQuestion(QuestionRequest questionRequest) {
        if (questionRepository.findByContent(questionRequest.getContent()).isPresent()) {
            return Constants.DATA_CONFLICT;
        }

        Question question = new Question();
        QuestionMapper.toEntity(questionRequest, question);
        questionRepository.save(question);
        return Constants.SUCCESS;
    }

    public int deleteQuestion(Long id) {
        if (questionRepository.findById(id).isPresent()) {
            questionRepository.deleteById(id);
            return Constants.SUCCESS;
        }

        return Constants.FAILURE;
    }

    public int updateQuestion(QuestionRequest questionRequest) {
        return questionRepository.findById(questionRequest.getId()).map(question -> {
            QuestionMapper.toEntity(questionRequest, question);
            questionRepository.save(question);
            return Constants.SUCCESS;
        }).orElse(Constants.FAILURE);
    }

    public List<ExamResponse.ExamData> getAllExams() {
        return examRepository.findAll().stream().map(exam -> {
            Subject subjectExam = subjectRepository.findById((long) exam.getSubject_id()).orElse(new Subject());
            List<Integer> idQuestions = ConvertUtils.convertStringToListNumber(exam.getQuestions());
            List<QuestionResponse.QuestionData> questionData = idQuestions.stream().map(id -> {
                Question question = questionRepository.findById((long) id).orElse(new Question());
                Subject subjectQuestion = subjectRepository.findById((long) exam.getSubject_id()).orElse(new Subject());
                return QuestionMapper.toQuestionData(question, subjectQuestion);
            }).toList();
            return ExamMapper.toExamData(exam, subjectExam, questionData);
        }).collect(Collectors.toList());
    }

    public int addExam(ExamRequest examRequest) {
        if (examRepository.findByTitle(examRequest.getTitle()).isPresent()) {
            return Constants.DATA_CONFLICT;
        }

        Exam exam = new Exam();
        ExamMapper.toEntity(examRequest, exam);
        examRepository.save(exam);
        return Constants.SUCCESS;
    }

    public int deleteExam(Long id) {
        if (examRepository.findById(id).isPresent()) {
            examRepository.deleteById(id);
            return Constants.SUCCESS;
        }

        return Constants.FAILURE;
    }

    public int updateExam(ExamRequest examRequest) {
        return examRepository.findById(examRequest.getId())
                .map(exam -> {
                    ExamMapper.toEntity(examRequest, exam);
                    examRepository.save(exam);
                    return Constants.SUCCESS;
                })
                .orElse(Constants.FAILURE);
    }
}
