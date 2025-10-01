package br.com.bgrbarbosa.ms_exame.business.impl;

import br.com.bgrbarbosa.ms_exame.business.ExamTypeService;
import br.com.bgrbarbosa.ms_exame.config.Messages;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import br.com.bgrbarbosa.ms_exame.infrastructure.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_exame.infrastructure.repository.ExamTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamTypeServiceImpl implements ExamTypeService {

    private final ExamTypeRepository repository;

    @Override
    public ExamType insert(ExamType entity) {
        return repository.save(entity);
    }

    @Override
    public List<ExamType> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public ExamType findById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
    }

    @Override
    public void delete(UUID uuid) {
        if (!repository.existsById(uuid)) {
            throw new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND);
        }
        repository.deleteById(uuid);
    }

    @Override
    public ExamType update(ExamType entity) {
        ExamType aux = repository.findById(entity.getCodExamType()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
        aux.setExams(entity.getExams());
        aux.setDescExame(entity.getDescExame());
        return repository.save(aux);
    }
}
