package net.javaguides.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exam_answers")
public class ExamAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "result_id")
    private int resultId;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "chosen_answer")
    private int chosenAnswer;
}
