package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SchedulingExamsRepository extends JpaRepository<Scheduling, UUID> {
    Optional<Scheduling> findByProtocol(Long protocol);
}
