package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para retornar o token JWT ao cliente após login.
 */
@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
}
