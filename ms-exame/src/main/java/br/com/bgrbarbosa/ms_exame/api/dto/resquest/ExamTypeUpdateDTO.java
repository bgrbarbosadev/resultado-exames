package br.com.bgrbarbosa.ms_exame.api.dto.resquest;

import br.com.bgrbarbosa.ms_exame.config.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ExamTypeUpdateDTO(

        UUID codExamType,

        @NotBlank(message = Messages.NOT_BLANK)
        @Size(min = 5, max = 50, message = Messages.FIELD_SIZE_MESSAGE)
        String descExame
) { }
