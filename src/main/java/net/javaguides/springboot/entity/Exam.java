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

    private int subject_id;
    private String title;
    private int duration_seconds;
    private String questions;

    @CreationTimestamp   // tự động set thời gian khi insert
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
