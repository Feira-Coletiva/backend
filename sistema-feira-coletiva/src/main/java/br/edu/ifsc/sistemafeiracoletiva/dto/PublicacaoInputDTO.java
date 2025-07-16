package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O Publicação envia apenas esses campos no JSON da requisição.
 */
@Data
public class PublicacaoInputDTO {

    @NotBlank(message = "Data final de exposição é obrigatório")
    private LocalDate dtFinalExposicao;

    @NotBlank(message = "Data final de exposição é obrigatório")
    private LocalDate dtFinalPagamento;

    @NotBlank(message = "O unidade de medida é obrigatório")
    private String etapa;

    @NotNull(message = "Relação com local de retirada é obrigatória")
    private Integer idLocalDeRetirada;

    @NotNull(message = "Relação com oferta é obrigatória")
    private Integer idOferta;
}
