package br.com.bgrbarbosa.ms_notification.business.impl;


import br.com.bgrbarbosa.ms_notification.business.NotificationService;
import br.com.bgrbarbosa.ms_notification.config.Messages;
import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import br.com.bgrbarbosa.ms_notification.infraestruture.exceptions.ResourceNotFoundException;
import br.com.bgrbarbosa.ms_notification.infraestruture.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public List<Notification> findAll(Pageable page) {
        return repository.findAll();
    }

    @Override
    public List<Notification> findAll() {
        return repository.findAll();
    }

    @Override
    public Notification findById(String uuid) {
        return repository.findById(uuid).orElseThrow(
                () -> new ResourceNotFoundException(Messages.RESOURCE_NOT_FOUND)
        );
    }

}
