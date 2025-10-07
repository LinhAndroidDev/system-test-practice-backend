package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.DeleteExamAnswerRequest;
import net.javaguides.springboot.response.ExamAnswerResponse;
import net.javaguides.springboot.service.ExamResultService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/exam_answers")
public class ExamAnswerController extends BaseController<ExamAnswerResponse, ExamAnswerResponse.ExamResultOfAnswer> {

    @Autowired
    ExamResultService examResultService;

    public ExamAnswerController() {
        super(ExamAnswerResponse.class);
    }

    @GetMapping("/get_exam_answers/{examResultId}")
    ResponseEntity<?> getAllExamAnswers(@PathVariable int examResultId) {
        try {
            ExamAnswerResponse.ExamResultOfAnswer examResults = examResultService.getAllAnswersByExamResultId(examResultId);
            return handleSuccess(examResults, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PostMapping("/delete")
    ResponseEntity<?> deleteExamAnswers(DeleteExamAnswerRequest request) {
        try {
            examResultService.deleteExamAnswerByResultId(request);
            return handleSuccess(null, "Deleted Successfully", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }
}
