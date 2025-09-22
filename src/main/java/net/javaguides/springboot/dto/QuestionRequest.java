package net.javaguides.springboot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private Long id;
    private int subject_id;
    private String content;
    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private int correct_answer;
}
