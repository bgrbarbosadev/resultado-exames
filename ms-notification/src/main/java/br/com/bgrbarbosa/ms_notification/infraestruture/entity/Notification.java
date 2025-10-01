package br.com.bgrbarbosa.ms_notification.infraestruture.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "notification")
public class Notification {

    @Id
    private String codNotification = UUID.randomUUID().toString();
    private String codScheduling;
    private String emailRecipient;
    private String cel;
    private String message;
}
