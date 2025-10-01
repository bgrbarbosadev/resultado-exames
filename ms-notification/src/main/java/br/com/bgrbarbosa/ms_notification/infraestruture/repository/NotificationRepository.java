package br.com.bgrbarbosa.ms_notification.infraestruture.repository;


import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
