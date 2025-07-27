package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O oferta envia apenas esses campos no JSON da requisição.
 */
@Data
public class OfertaProdutosInputDTO {

    @NotBlank(message = "O título da oferta é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição da oferta é obrigatória")
    private String descricao;

    @NotNull(message = "O ID do vendedor é obrigatório")
    private Integer vendedorId; // Para identificar o vendedor logado ou associado

    @NotEmpty(message = "A oferta deve conter pelo menos um produto")
    @Valid // Para validar os ProdutoDTOs dentro da lista
    private List<ProdutoParaOfertaInputDTO> produtos;
}
