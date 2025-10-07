package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.ExamRequest;
import net.javaguides.springboot.response.ExamResponse;
import net.javaguides.springboot.service.ExamService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/exam")
public class ExamController extends BaseController<ExamResponse, List<ExamResponse.ExamData>> {

    @Autowired
    private ExamService examService;

    public ExamController() {
        super(ExamResponse.class);
    }

    @GetMapping("/get_exams")
    ResponseEntity<?> getAllExams() {
        try {
            List<ExamResponse.ExamData> examDataList = examService.getAllExams();
            return handleSuccess(examDataList, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PostMapping("/add_exam")
    ResponseEntity<?> addExam(@RequestBody ExamRequest request) {
        try {
            int status = examService.addExam(request);
            List<ExamResponse.ExamData> examDataList = examService.getAllExams();
            String message;
            if (status == Constants.DATA_CONFLICT) {
                message = "Exam already exists";
            } else  {
                message = "Exam added successfully";
            }
            return handleSuccess(examDataList, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteExam(@PathVariable Long id) {
        try {
            int status = examService.deleteExam(id);
            List<ExamResponse.ExamData> examDataList = examService.getAllExams();
            String message;
            if (status == Constants.FAILURE) {
                message = "Exam not found";
            } else {
                message = "Exam deleted successfully";
            }
            return handleSuccess(examDataList, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PutMapping("/update")
    ResponseEntity<?> updateExam(@RequestBody ExamRequest request) {
        try {
            int status = examService.updateExam(request);
            List<ExamResponse.ExamData> examDataList = examService.getAllExams();
            String message;
            if (status == Constants.FAILURE) {
                message = "Exam not found";
            } else {
                message = "Exam updated successfully";
            }
            return handleSuccess(examDataList, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }
}
