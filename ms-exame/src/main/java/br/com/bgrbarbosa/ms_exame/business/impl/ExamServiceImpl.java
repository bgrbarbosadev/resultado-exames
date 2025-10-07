package br.com.bgrbarbosa.ms_exame.business.impl;

import br.com.bgrbarbosa.ms_exame.business.ExamService;
import br.com.bgrbarbosa.ms_exame.business.producer.PublishedExamService;
import br.com.bgrbarbosa.ms_exame.config.Messages;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import br.com.bgrbarbosa.ms_exame.infrastructure.exceptions.DatabaseException;
import br.com.bgrbarbosa.ms_exame.infrastructure.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_exame.infrastructure.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository repository;

    private final PublishedExamService producerExamService;

    private final RestTemplate restTemplate;

    private final String URL_EXAM_SCHEDLING = "http://ms-scheduling/scheduling/exams";

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
    public void delete(UUID uuid) throws Exception {
        Exam exam = repository.findById(uuid).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));

        if (existExamByScheduling(uuid)) {
            throw new DatabaseException(Messages.EXISTING_EXAM);
        }

        try {
            repository.deleteById(exam.getCodExam());
            producerExamService.deleteExam(exam);
        } catch (Exception ex) {
            throw new Exception(Messages.ERROR_INSERTING_RECORD + exam.getCodExam());
        }

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

    public Boolean existExamByScheduling(UUID uuid){
        boolean exists = false;
        try {
            String urlDelecao = URL_EXAM_SCHEDLING + "/" + uuid;
            Boolean isExamShceduling = restTemplate.getForObject(urlDelecao, Boolean.class);
            exists = (isExamShceduling != null && isExamShceduling) ? true : false;

        } catch (Exception e) {
            new Exception(e.getMessage());
        }

        return exists;
    }
}
