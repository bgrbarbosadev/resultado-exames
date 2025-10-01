package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, UUID> {
}
