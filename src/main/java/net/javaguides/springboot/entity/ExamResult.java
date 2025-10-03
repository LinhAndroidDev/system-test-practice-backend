package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_id")
    private int examId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "number_correct_answers")
    private int numberCorrectAnswers;

    @Column(name = "total_questions")
    private int totalQuestions;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
