package br.com.bgrbarbosa.ms_notification.api.dto.request;

public record NotificationRequestDTO(
    String codScheduling,
    String emailRecipient,
    String cel,
    String message
) { }
