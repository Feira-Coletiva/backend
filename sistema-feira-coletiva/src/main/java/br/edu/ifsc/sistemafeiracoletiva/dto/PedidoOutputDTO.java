package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO usado para saída de dados (GET, POST response) de um Pedido.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoOutputDTO {
    private Integer id;
    private ProdutoSimplesOutputDTO produto; // Detalhes do produto
    private Integer qtdProdutos;
    private BigDecimal precoUnitarioNoPedido;
    private BigDecimal valorTotalItem;
}