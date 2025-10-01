package br.com.bgrbarbosa.ms_exame.business;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExamService {

    Exam insert(Exam entity) throws Exception;

    List<Exam> findAll(Pageable page);

    Exam findById(UUID uuid);

    void delete(UUID uuid);

    Exam update(Exam entity) throws Exception;
}
