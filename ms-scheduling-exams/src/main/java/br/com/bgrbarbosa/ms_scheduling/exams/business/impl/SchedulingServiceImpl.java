package br.com.bgrbarbosa.ms_scheduling.exams.business.impl;

import br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response.NotificationDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.business.SchedulingService;
import br.com.bgrbarbosa.ms_scheduling.exams.business.producer.PublishedSchedulingService;
import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Scheduling;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.LaudoNotFoundException;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.repository.SchedulingExamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService {

    private final SchedulingExamsRepository repository;

    private final PublishedSchedulingService publishedExamResult;

    @Override
    public Scheduling insert(Scheduling entity) {
        return repository.save(entity);
    }

    @Override
    public List<Scheduling> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public Scheduling findById(UUID uuid) {
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
    public Scheduling update(Scheduling entity) throws Exception {
        Scheduling aux = repository.findById(entity.getCodScheduling()).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        aux.setCodScheduling(entity.getCodScheduling());
        aux.setStatus(entity.getStatus());
        aux.setVlExam(entity.getVlExam());
        aux.setDate_exam(entity.getDate_exam());
        aux.setDate_result(entity.getDate_result());
        aux.setCustomer(entity.getCustomer());
        aux.setExamList(entity.getExamList());
        aux.setUrl_laudo(entity.getUrl_laudo());

        if (aux.getUrl_laudo().isBlank() && aux.getStatus().toString().equals("FINISH")) {
            throw new LaudoNotFoundException(Messages.REPORT_NOT_ATTACHED);
        }

        if (aux.getStatus().toString().equals("FINISH")) {
            Scheduling result = repository.save(aux);
            NotificationDTO notificationDTO = new NotificationDTO(
                    result.getCodScheduling(),
                    result.getCustomer().getEmail(),
                    result.getCustomer().getCel(),
                    Messages.MESSAGE_NOTIFICATION);
            publishedNotification(notificationDTO);
        } else {
            aux = repository.save(aux);
        }

        return aux;
    }

    @Override
    public Scheduling findByExamProtocol(Long protocol, String password, String cpf) {
        Scheduling aux = repository.findByProtocol(protocol).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND));
        if (protocol.equals(aux.getProtocol())
                && password.equals(aux.getPassword())
                && cpf.equals(aux.getCustomer().getCpf())) {
            return aux;
        } else {
            return null;
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-notification"),
            exchange = @Exchange(value = "exchange-exam-scheduling", type = ExchangeTypes.DIRECT),
            key = "notification"))
    public void publishedNotification(NotificationDTO entity) throws Exception {
        publishedExamResult.createUpdateScheduling(entity);
    }

}
