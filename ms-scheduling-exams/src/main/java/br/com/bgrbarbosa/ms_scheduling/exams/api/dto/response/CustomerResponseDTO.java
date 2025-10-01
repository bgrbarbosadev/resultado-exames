package br.com.bgrbarbosa.ms_scheduling.exams.api.dto.response;


import java.util.UUID;

public record CustomerResponseDTO(

        UUID codCustomer,
        String name
) { }
