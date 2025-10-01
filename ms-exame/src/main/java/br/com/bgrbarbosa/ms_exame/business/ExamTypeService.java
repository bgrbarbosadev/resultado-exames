package br.com.bgrbarbosa.ms_exame.business;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ExamTypeService {

    ExamType insert(ExamType entity);

    List<ExamType> findAll(Pageable page);

    ExamType findById(UUID uuid);

    void delete(UUID uuid);

    ExamType update(ExamType entity);
}
