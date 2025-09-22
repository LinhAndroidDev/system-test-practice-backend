package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByContent(String content);
}
