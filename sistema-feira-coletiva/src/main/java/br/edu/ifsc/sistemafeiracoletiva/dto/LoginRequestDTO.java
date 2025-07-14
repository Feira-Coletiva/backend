package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para receber dados de login do cliente.
 */
@Data
@AllArgsConstructor
public class LoginRequestDTO {

    private String email;
    private String senha;
}
