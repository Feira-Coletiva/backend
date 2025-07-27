package br.edu.ifsc.sistemafeiracoletiva.dto;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.UnidadeDeMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O oferta envia apenas esses campos no JSON da requisição.
 */
@Data
public class ProdutoParaOfertaInputDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "A categoria do produto é obrigatória")
    private String categoria; // Nome da categoria, não o ID

    @NotNull(message = "A unidade de medida é obrigatória")
    private UnidadeDeMedida unidadeMedida;

    @NotNull(message = "A medida é obrigatória")
    @Min(value = 0, message = "A medida não pode ser negativa")
    private Double medida;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    private Double preco;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 1, message = "A quantidade em estoque deve ser pelo menos 1") // Pelo menos um produto para a oferta
    private Integer qtdEstoque;
}
