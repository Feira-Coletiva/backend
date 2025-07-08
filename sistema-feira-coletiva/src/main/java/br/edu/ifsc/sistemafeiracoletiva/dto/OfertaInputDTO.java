package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O oferta envia apenas esses campos no JSON da requisição.
 */
@Data
public class OfertaInputDTO {

    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 3, max = 100, message = "O titulo deve ter entre 3 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "O descrição é obrigatório")
    @Size(min = 10, max = 250, message = "A descrição deve ter entre 10 e 250 caracteres")
    private String descricao;

    @NotNull(message = "ID de vendedor é obrigatório")
    private Integer idVendedor;
}
