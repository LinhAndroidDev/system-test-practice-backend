package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.SubjectRequest;
import net.javaguides.springboot.entity.Subject;
import net.javaguides.springboot.response.SubjectResponse;
import net.javaguides.springboot.service.ExamService;
import net.javaguides.springboot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController extends BaseController<SubjectResponse, List<Subject>> {

    @Autowired
    private ExamService examService;

    public SubjectController() {
        super(SubjectResponse.class);
    }

    @GetMapping("/get_subjects")
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<Subject> subjects = examService.getAllSubjects();
            return handleSuccess(subjects, "Success", Constants.SUCCESS);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PostMapping("/add_subject")
    public ResponseEntity<?> addSubject(@RequestBody SubjectRequest subject) {
        try {
            int status = examService.addSubject(subject.getName_subject());
            List<Subject> subjects = examService.getAllSubjects();
            String message;
            if (status == Constants.DATA_CONFLICT) {
                message = "Subject already exists";
            } else {
                message = "Subject added successfully";
            }
            return handleSuccess(subjects, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            int status = examService.deleteSubject(id);
            List<Subject> subjects = examService.getAllSubjects();
            String message;
            if (status == Constants.FAILURE) {
                message = "Subject Not Found";
            }  else {
                message = "Subject Deleted Successfully!";
            }
            return handleSuccess(subjects, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectRequest subjectRequest) {
        try {
            int status = examService.updateSubject(subjectRequest);
            List<Subject> subjects = examService.getAllSubjects();
            String message;
            if (status == Constants.FAILURE) {
                message = "Subject Not Found";
            } else {
                message = "Subject Updated Successfully!";
            }
            return handleSuccess(subjects, message, status);
        } catch (HttpClientErrorException e) {
            return handleException(e);
        }
    }
}
