package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest;

import br.com.bgrbarbosa.ms_scheduling.exams.config.Messages;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record SchedulingResultDTO(

    @NotNull(message = Messages.NOT_NULL)
    Long protocol,
    @NotNull(message = Messages.NOT_NULL)
    String password,
    @NotNull(message = Messages.NOT_NULL)
    @CPF
    String cpf)
{ }
