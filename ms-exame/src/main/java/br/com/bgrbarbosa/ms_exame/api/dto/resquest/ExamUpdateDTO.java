package br.com.bgrbarbosa.ms_exame.api.dto.resquest;

import br.com.bgrbarbosa.ms_exame.config.Messages;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ExamUpdateDTO(

      UUID codExam,

      @NotBlank(message = Messages.NOT_BLANK)
      @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
      String descExam,

      @NotNull(message = Messages.NOT_NULL)
      Double vlExame,

      @NotBlank(message = Messages.NOT_BLANK)
      @Size(min = 3, max = 80, message = Messages.FIELD_SIZE_MESSAGE)
      String orientationExam,

      @NotNull(message = Messages.NOT_NULL)
      ExamType examType
) { }
