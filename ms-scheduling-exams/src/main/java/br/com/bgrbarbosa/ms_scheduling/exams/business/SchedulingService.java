package br.com.bgrbarbosa.ms_scheduling.exams.business;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface SchedulingService {

    Scheduling insert(Scheduling entity);

    List<Scheduling> findAll(Pageable page);

    Scheduling findById(UUID uuid);

    void delete(UUID uuid);

    Scheduling update(Scheduling entity) throws Exception;

    Scheduling findByExamProtocol(Long protocol, String password, String cpf);

}
