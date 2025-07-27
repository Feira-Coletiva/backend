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
public class ParticipanteOutputDTO {
    private int id;
    private double valorTotal;
    private int qtdTotalProdutos;
    private boolean statusPago;
}


