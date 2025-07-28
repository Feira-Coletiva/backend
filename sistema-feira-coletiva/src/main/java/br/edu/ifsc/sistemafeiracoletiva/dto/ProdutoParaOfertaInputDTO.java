package br.edu.ifsc.sistemafeiracoletiva.dto;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.UnidadeDeMedida;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O oferta envia apenas esses campos no JSON da requisição.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoParaOfertaInputDTO {
    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "A categoria do produto é obrigatória")
    private String categoria;

    @NotNull(message = "A unidade de medida é obrigatória")
    private UnidadeDeMedida unidadeMedida;

    @NotNull(message = "A medida é obrigatória")
    @Min(value = 0, message = "A medida não pode ser negativa")
    private Double medida; // Mantido como Double, pode ser BigDecimal se necessário

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço não pode ser negativo")
    private BigDecimal preco; // ✅ Alterado para BigDecimal

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @Min(value = 1, message = "A quantidade em estoque deve ser pelo menos 1")
    private Integer qtdEstoque;
}
