package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {

}
