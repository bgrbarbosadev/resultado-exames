package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response;

import java.util.UUID;

public record NotificationDTO(
    UUID codScheduling,
    String emailRecipient,
    String cel,
    String message

) { }
