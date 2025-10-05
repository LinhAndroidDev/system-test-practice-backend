package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.ExamAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Long> {
    Optional<List<ExamAnswer>> findByResultId(int resultId);
}
