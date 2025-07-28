package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicacaoResumoOutputDTO {
    private Integer id;
    private String tituloOferta; // Título da oferta relacionada à publicação
    private LocalDate dtFinalExposicao;
    private String nomeLocalDeRetirada; // Nome do local de retirada
    private String etapa;
}
