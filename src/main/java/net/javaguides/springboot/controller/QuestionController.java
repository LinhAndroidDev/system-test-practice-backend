package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.QuestionRequest;
import net.javaguides.springboot.response.QuestionResponse;
import net.javaguides.springboot.service.ExamService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController extends BaseController<QuestionResponse, List<QuestionResponse.QuestionData>> {

    @Autowired
    private ExamService examService;

    protected QuestionController(Class<QuestionResponse> responseClass) {
        super(responseClass);
    }

    @GetMapping("/get_questions")
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<QuestionResponse.QuestionData> questions = examService.getAllQuestions();
            return handleSuccess(questions, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PostMapping("/add_question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest questionRequest) {
        try {
            int status = examService.addQuestion(questionRequest);
            List<QuestionResponse.QuestionData> questions = examService.getAllQuestions();
            String message;
            if (status == Constants.DATA_CONFLICT) {
                message = "Question already exists";
            } else {
                message = "Question added successfully";
            }
            return handleSuccess(questions, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            int status = examService.deleteQuestion(id);
            List<QuestionResponse.QuestionData> questions = examService.getAllQuestions();
            String message;
            if (status == Constants.FAILURE) {
                message = "Question not found";
            } else {
                message = "Question deleted successfully";
            }
            return handleSuccess(questions, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionRequest questionRequest) {
        try {
            int status = examService.updateQuestion(questionRequest);
            List<QuestionResponse.QuestionData> questions = examService.getAllQuestions();
            String message;
            if (status == Constants.FAILURE) {
                message = "Question not found";
            } else {
                message = "Question updated successfully";
            }
            return handleSuccess(questions, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }
}
