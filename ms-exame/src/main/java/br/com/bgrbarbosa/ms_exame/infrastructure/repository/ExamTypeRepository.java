package br.com.bgrbarbosa.ms_exame.infrastructure.repository;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExamTypeRepository extends JpaRepository<ExamType, UUID>{
}
