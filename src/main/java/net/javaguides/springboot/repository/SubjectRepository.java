package net.javaguides.springboot.repository;

import net.javaguides.springboot.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByNameSubject(String name);
}
