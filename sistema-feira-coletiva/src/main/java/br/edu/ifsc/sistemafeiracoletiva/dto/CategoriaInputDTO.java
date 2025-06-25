package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O categoria envia apenas esses campos no JSON da requisição.
 */
@Data
public class CategoriaInputDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;
}
