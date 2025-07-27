package br.edu.ifsc.sistemafeiracoletiva.dto;


import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O pedido envia apenas esses campos no JSON da requisição.
 */
@Data
public class PedidoInputDTO {
    private String descricacao;
    private Integer qtdProduto;
    private Double valor;
    private Integer idParticipante;
}
