package br.com.bgrbarbosa.ms_notification.business;

import br.com.bgrbarbosa.ms_notification.api.dto.response.NotificationResponseDTO;

public interface MailService {

    void sendMail(NotificationResponseDTO dto);

}
