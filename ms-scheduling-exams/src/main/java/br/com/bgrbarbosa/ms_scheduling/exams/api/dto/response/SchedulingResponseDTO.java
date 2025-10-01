package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response;

import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Customer;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.Exam;
import br.com.bgrbarbosa.ms_scheduling.exams.infraestruture.entity.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record SchedulingResponseDTO(
    UUID codScheduling,
    Status status,
    Double vlExam,
    Long protocol,
    String password,
    LocalDate date_exam,
    LocalDate date_result,
    String url_laudo,
    Customer customer,
    List<Exam> examList)
{ }
