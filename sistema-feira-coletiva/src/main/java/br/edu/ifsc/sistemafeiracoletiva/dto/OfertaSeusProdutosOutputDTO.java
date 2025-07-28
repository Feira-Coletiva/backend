package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO usado para saída de dados oferta join produtos (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaSeusProdutosOutputDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Integer qtdEstoqueTotal; // Adicionado
    private Boolean statusDisponibilidade; // Nome ajustado
    private VendedorOutputDTO vendedor;
    private List<ResumoProdutoOfertaDTO> produtos;
}
