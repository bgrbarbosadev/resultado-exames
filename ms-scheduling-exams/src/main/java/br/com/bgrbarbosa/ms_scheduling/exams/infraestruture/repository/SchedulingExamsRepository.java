package br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SchedulingExamsRepository extends JpaRepository<Scheduling, UUID> {
    Optional<Scheduling> findByProtocol(Long protocol);

    boolean existsByCustomer_CodCustomer(UUID customerUuid);

    boolean existsByExamList_CodExam(UUID examUuid);
}
