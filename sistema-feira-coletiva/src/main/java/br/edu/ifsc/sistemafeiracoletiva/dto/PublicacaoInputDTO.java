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
    @NotNull(message = "Data final de exposição é obrigatória")
    private LocalDate dtFinalExposicao;

    @NotNull(message = "Data final de exposição é obrigatória")
    private LocalDate dtFinalPagamento;

    // Etapa é definida no backend, não precisa vir do frontend

    @NotNull(message = "Relação com local de retirada é obrigatória")
    private Integer idLocalDeRetirada;

    @NotNull(message = "Relação com oferta é obrigatória")
    private Integer idOferta;
}