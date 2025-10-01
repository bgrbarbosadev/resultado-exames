package br.com.bgrbarbosa.ms_notification.api.dto.response;

public record NotificationResponseDTO(
    String codNotification,
    String codScheduling,
    String emailRecipient,
    String cel,
    String message
) { }
