package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.QuestionRequest;
import net.javaguides.springboot.dto.SubjectRequest;
import net.javaguides.springboot.entity.Question;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.mapper.QuestionMapper;
import net.javaguides.springboot.repository.QuestionRepository;
import net.javaguides.springboot.repository.SubjectRepository;
import net.javaguides.springboot.response.QuestionResponse;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    QuestionRepository questionRepository;

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
}
