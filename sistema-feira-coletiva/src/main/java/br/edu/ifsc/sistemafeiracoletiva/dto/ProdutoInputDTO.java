package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O produto envia apenas esses campos no JSON da requisição.
 */
@Data
public class ProdutoInputDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O titulo deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "O categoria é obrigatório")
    private Integer idCategoria;

    @NotBlank(message = "O unidade de medida é obrigatório")
    private String unidadeMedida;

    @NotNull(message = "O medida é obrigatório")
    private Double medida;

    @NotNull(message = "O preço é obrigatório")
    private Double preco;

    @NotNull(message = "O quantidade em estoque é obrigatório")
    private Integer qtdEstoque;

    @NotNull(message = "Relação com oferta é obrigatória")
    private Integer idOferta;
}
