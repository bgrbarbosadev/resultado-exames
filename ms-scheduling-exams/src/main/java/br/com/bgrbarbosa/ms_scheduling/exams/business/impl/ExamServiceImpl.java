package br.com.bgrbarbosa.ms_scheduling.exams.business.impl;

import br.com.bgrbarbosa.ms_scheduling.exams.business.ExamService;
import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repository;

    @Override
    public Exam insert(Exam entity) {
        return repository.save(entity);
    }

    @Override
    public List<Exam> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public Exam findById(UUID uuid) {
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
    public Exam update(Exam entity) {
        Exam aux = repository.findById(entity.getCodExam()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
        aux.setDescExam(entity.getDescExam());
        aux.setVlExame(entity.getVlExame());
        aux.setOrientationExam(entity.getOrientationExam());
        return repository.save(aux);
    }
}
