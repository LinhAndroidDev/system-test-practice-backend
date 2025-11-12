package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ExamResultRequest;
import net.javaguides.springboot.response.ExamResultResponse;
import net.javaguides.springboot.response.ListExamResultResponse;
import net.javaguides.springboot.service.ExamResultService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/exam_result")
public class ExamResultController extends BaseController<ListExamResultResponse, List<ExamResultResponse.ExamResultData>> {

    @Autowired
    private ExamResultService examResultService;

    public ExamResultController() {
        super(ListExamResultResponse.class);
    }

    @GetMapping("/get_exam_results")
    ResponseEntity<?> getAllExamResults() {
        try {
            List<ExamResultResponse.ExamResultData> examResultDataList = examResultService.getAllExamResults();
            return handleSuccess(examResultDataList, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PostMapping
    ResponseEntity<?> addExamResult(@RequestBody ExamResultRequest request) {
        try {
            if (request.getExamAnswers() == null) {
                throw new HttpClientErrorException(org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid request data");
            }
            Long idResult = examResultService.addExamResult(request);
            ExamResultResponse.ExamResultData examResultData = examResultService.getExamResultById(idResult);
            ExamResultResponse response = new ExamResultResponse();
            response.setData(examResultData);
            response.setMessage("Exam result added successfully");
            response.setStatus(Constants.SUCCESS);
            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteExamResult(@PathVariable Long id) {
        try {
            int status = examResultService.deleteExamResult(id);
            List<ExamResultResponse.ExamResultData> examResultDataList = examResultService.getAllExamResults();
            String message;
            if (status == Constants.FAILURE) {
                message = "Exam result not found";
            } else {
                message = "Exam result deleted successfully";
            }
            return handleSuccess(examResultDataList, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PutMapping
    ResponseEntity<?> updateExamResult(ExamResultRequest request) {
        try {
            int status = examResultService.updateExamResult(request);
            List<ExamResultResponse.ExamResultData> examResultDataList = examResultService.getAllExamResults();
            String message;
            if (status == Constants.FAILURE) {
                message = "Exam result not found";
            } else {
                message = "Exam result updated successfully";
            }
            return handleSuccess(examResultDataList, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }
}
