package br.com.bgrbarbosa.ms_exame.api.dto.response;

import br.com.bgrbarbosa.ms_exame.config.MoneyConverter;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;
import br.com.bgrbarbosa.ms_exame.infrastructure.entity.ExamType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.UUID;

public record ExamResponseDTO(
        UUID codExam,
        String descExam,
        @JsonSerialize(using = MoneyConverter.class)
        Double vlExame,
        String orientationExam,
        ExamType examType)
{ }
