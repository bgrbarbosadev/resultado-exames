package br.com.bgrbarbosa.ms_notification.business;

import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {

    List<Notification> findAll(Pageable page);
    List<Notification> findAll();
    Notification findById(String uuid);

}
