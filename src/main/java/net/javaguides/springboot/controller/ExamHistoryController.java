package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.DeleteExamAnswerRequest;
import net.javaguides.springboot.response.ExamHistoryResponse;
import net.javaguides.springboot.service.ExamResultService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/exam_history")
public class ExamHistoryController extends BaseController<ExamHistoryResponse, ExamHistoryResponse.ExamResultOfAnswer> {

    @Autowired
    ExamResultService examResultService;

    public ExamHistoryController() {
        super(ExamHistoryResponse.class);
    }

    @GetMapping
    ResponseEntity<?> getAllExamAnswers(@RequestParam int examResultId) {
        try {
            ExamHistoryResponse.ExamResultOfAnswer examResults = examResultService.getAllAnswersByExamResultId(examResultId);
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
