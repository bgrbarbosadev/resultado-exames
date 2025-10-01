package br.com.bgrbarbosa.ms_user.api.dto;


import br.com.bgrbarbosa.ms_user.config.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RoleDTO(
        Long idRole,

        @NotEmpty(message = Messages.NOT_EMPTY)
        @Size(min = 6, max = 50, message = Messages.FIELD_SIZE_MESSAGE)
        String role
) { }
