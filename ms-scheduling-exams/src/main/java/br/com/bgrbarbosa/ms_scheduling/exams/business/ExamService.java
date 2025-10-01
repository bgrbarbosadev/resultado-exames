package br.com.bgrbarbosa.ms_scheduling.exams.business;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExamService {

    Exam insert(Exam entity);

    List<Exam> findAll(Pageable page);

    Exam findById(UUID uuid);

    void delete(UUID uuid);

    Exam update(Exam entity);
}
