package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O Local De Retirada envia apenas esses campos no JSON da requisição.
 */
@Data
public class LocalDeRetiradaInputDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

}
