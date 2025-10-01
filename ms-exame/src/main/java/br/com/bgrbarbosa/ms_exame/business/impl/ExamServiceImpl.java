package br.com.bgrbarbosa.ms_exame.business.impl;

import br.com.bgrbarbosa.ms_exame.business.ExamService;
import br.com.bgrbarbosa.ms_exame.business.producer.PublishedExamService;
import br.com.bgrbarbosa.ms_exame.config.Messages;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import br.com.bgrbarbosa.ms_exame.infrastructure.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_exame.infrastructure.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repository;

    private final PublishedExamService producerExamService;

    @Override
    public Exam insert(Exam entity) throws Exception {
        Exam result = new Exam();
        try {
            result = repository.save(entity);
            producerExamService.createExam(result);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + result.getCodExam());
        }
        return result;
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
    public Exam update(Exam entity) throws Exception {
        Exam aux = repository.findById(entity.getCodExam()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
        aux.setExamType(entity.getExamType());
        aux.setDescExam(entity.getDescExam());
        aux.setVlExame(entity.getVlExame());
        aux.setOrientationExam(entity.getOrientationExam());
        Exam result = new Exam();
        try {
            result = repository.save(aux);
            producerExamService.updateExam(result);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + result.getCodExam());
        }
        return result;
    }
}
