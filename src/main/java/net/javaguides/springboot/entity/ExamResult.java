package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "exam_results")
public class ExamResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int exam_id;
    private int user_id;
    private Float score;
    private LocalDateTime submitted_at;
}
