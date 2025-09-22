package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int subject_id;
    private String content;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private int correct_answer;
}
