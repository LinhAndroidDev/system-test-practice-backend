package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exam_answers")
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int result_id;
    private int question_id;
    private int chosen_answer;
}
