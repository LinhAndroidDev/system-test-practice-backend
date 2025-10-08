package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_id")
    private int subjectId;

    @Column(name = "title")
    private String title;

    @Column(name = "duration_seconds")
    private int durationSeconds;

    @Column(name = "questions")
    private String questions;

    @CreationTimestamp   // tự động set thời gian khi insert
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
