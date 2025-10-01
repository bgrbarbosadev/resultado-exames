package br.com.bgrbarbosa.ms_user.api.dto;


import br.com.bgrbarbosa.ms_user.config.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(

        @NotBlank(message = Messages.NOT_BLANK)
        @Email(message = Messages.EMAIL_VALID)
        String email,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 6, max = 12, message = Messages.FIELD_SIZE_MESSAGE)
        String password
) { }
