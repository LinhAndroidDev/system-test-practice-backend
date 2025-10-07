package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ExamResultRequest;
import net.javaguides.springboot.response.ExamResultResponse;
import net.javaguides.springboot.service.ExamResultService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/exam_result")
public class ExamResultController extends BaseController<ExamResultResponse, List<ExamResultResponse.ExamResultData>> {

    @Autowired
    private ExamResultService examResultService;

    public ExamResultController() {
        super(ExamResultResponse.class);
    }

    @RequestMapping("/get_exam_results")
    ResponseEntity<?> getAllExamResults() {
        try {
            List<ExamResultResponse.ExamResultData> examResultDataList = examResultService.getAllExamResults();
            return handleSuccess(examResultDataList, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @RequestMapping("/add_exam_result")
    ResponseEntity<?> addExamResult(ExamResultRequest request) {
        try {
            examResultService.addExamResult(request);
            List<ExamResultResponse.ExamResultData> examResultDataList = examResultService.getAllExamResults();
            return handleSuccess(examResultDataList, "Exam result added successfully", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @RequestMapping("/delete/{id}")
    ResponseEntity<?> deleteExamResult(Long id) {
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

    @RequestMapping("/update_exam_result")
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
