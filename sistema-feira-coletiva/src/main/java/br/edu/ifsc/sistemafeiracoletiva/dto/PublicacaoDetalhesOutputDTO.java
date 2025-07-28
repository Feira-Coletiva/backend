package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoDetalhesOutputDTO {
    private Integer id;
    private LocalDate dtFinalExposicao;
    private LocalDate dtFinalPagamento;
    private String etapa;
    private LocalDeRetiradaOutputDTO localDeRetirada;
    private OfertaOutputDTO oferta;

    // ✅ NOVO: Lista de participantes desta publicação
    private List<ParticipanteResumoPublicacaoOutputDTO> participantes;
}