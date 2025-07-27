package br.edu.ifsc.sistemafeiracoletiva.dto;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O participante envia apenas esses campos no JSON da requisição.
 */
@Data
public class ParticipanteInputDTO {
    private Double valorTotal;
    private Integer qtdTotalProdutos;
    private Boolean statusPago;
    private Integer idCliente;
    private Integer idPublicacao;
}
