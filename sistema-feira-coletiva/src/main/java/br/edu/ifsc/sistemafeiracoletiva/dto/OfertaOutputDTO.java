package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO usado para saída de dados (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaOutputDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Integer qtdEstoqueTotal;
    private Boolean statusDisponibilidade;
    private VendedorOutputDTO vendedor;
    private List<ProdutoOutputDTO> produtos;
}
