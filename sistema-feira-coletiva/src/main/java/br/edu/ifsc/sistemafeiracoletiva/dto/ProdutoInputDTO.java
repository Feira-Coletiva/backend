package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

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
    @Min(value = 0, message = "A medida não pode ser negativa")
    private Double medida;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    private BigDecimal preco;

    @NotNull(message = "O quantidade em estoque é obrigatório")
    @Min(value = 1, message = "A quantidade em estoque deve ser pelo menos 1")
    private Integer qtdEstoque;

    @NotNull(message = "Relação com oferta é obrigatória")
    private Integer idOferta;
}
