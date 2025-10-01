package br.com.bgrbarbosa.ms_exame.api.dto.response;

import br.com.bgrbarbosa.ms_exame.infrastructure.entity.Exam;

import java.util.List;
import java.util.UUID;

public record ExamTypeResponseDTO(UUID codExamType, String descExame, List<Exam> exams) {
}
