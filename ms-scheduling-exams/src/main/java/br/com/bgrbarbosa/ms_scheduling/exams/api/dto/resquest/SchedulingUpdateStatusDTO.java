package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.resquest;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.enums.Status;

import java.util.UUID;

public record SchedulingUpdateStatusDTO(
        UUID codScheduling,
        Status status,
        String url_laudo)
{ }
