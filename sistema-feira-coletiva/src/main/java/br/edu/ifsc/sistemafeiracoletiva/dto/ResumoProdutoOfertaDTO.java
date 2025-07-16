package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado para saída de dados junto de Oferta (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 * Oferta -> info produtos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumoProdutoOfertaDTO {
    private Integer id;
    private String nome;
    private String unidadeMedida;
    private Double medida;
    private Double preco;
    private Integer qtdEstoque;
}
