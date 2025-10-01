package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest;

import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CustomerRequestDTO(

        UUID codCustomer,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
        String name,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 11, max = 11, message = Messages.FIELD_SIZE_MESSAGE)
        String cpf,

        @NotBlank(message = Messages.NOT_BLANK)
        @Email(message = Messages.EMAIL_VALID)
        String email,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 10, max = 13, message = Messages.FIELD_SIZE_MESSAGE)
        String tel,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 10, max = 13, message = Messages.FIELD_SIZE_MESSAGE)
        String cel,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
        String address,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 50, message = Messages.FIELD_SIZE_MESSAGE)
        String neighborhood,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 50, message = Messages.FIELD_SIZE_MESSAGE)
        String city,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 2, max = 50, message = Messages.FIELD_SIZE_MESSAGE)
        String state,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 8, max = 8, message = Messages.FIELD_SIZE_MESSAGE)
        String cep
) { }
