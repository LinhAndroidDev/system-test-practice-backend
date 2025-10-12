package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp   // tự động set thời gian khi insert
    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;
}
