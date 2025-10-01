package br.com.bgrbarbosa.ms_user.api.dto;

import br.com.bgrbarbosa.ms_user.config.Messages;
import br.com.bgrbarbosa.ms_user.infraestruture.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDTO(
        Long idUser,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
        String firstName,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
        String lastName,

        @NotBlank(message = Messages.NOT_BLANK)
        @Email(message = Messages.EMAIL_VALID)
        String email,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 6, max = 12, message = Messages.FIELD_SIZE_MESSAGE)
        String password,

        @NotEmpty(message = Messages.NOT_EMPTY)
        Set<Role> roles
) { }
