package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado para saída de dados (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoOutputDTO {
    private Integer id;
    private String nome;
    private CategoriaOutputDTO categoria;
    private String unidadeMedida;
    private Double medida;
    private Double preco;
    private Integer qtdEstoque;
}
