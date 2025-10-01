package br.com.bgrbarbosa.ms_customer.api.dto.response;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID codCustomer,
        String name,
        String cpf,
        String email,
        String tel,
        String cel,
        String address,
        String neighborhood,
        String city,
        String state,
        String cep)
{ }
