package br.com.bgrbarbosa.ms_exame.infrastructure.repository;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamRepository extends JpaRepository<Exam, UUID>{
}
