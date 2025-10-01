package br.com.bgrbarbosa.ms_oauth.infraestruture.entity;


public record UserRequestDTO(
        String email,
        String password
) { }
