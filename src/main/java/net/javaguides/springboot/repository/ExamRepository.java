package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam,Long> {
    Optional<Exam> findByTitle(String title);
}
