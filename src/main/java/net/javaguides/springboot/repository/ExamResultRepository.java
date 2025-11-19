package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.ExamResult;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {
    Optional<List<ExamResult>> findByUserId(Integer userId, Sort sort);
}
