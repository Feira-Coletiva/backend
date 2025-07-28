package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO usado para saída de dados (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoOutputDTO {
    private Integer id;
    private LocalDate dtFinalExposicao;
    private LocalDate dtFinalPagamento;
    private String etapa;
    private LocalDeRetiradaOutputDTO localDeRetirada;
    private OfertaOutputDTO oferta;
}
