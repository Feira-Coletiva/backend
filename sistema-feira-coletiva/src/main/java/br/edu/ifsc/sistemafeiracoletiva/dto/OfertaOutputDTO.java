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
public class OfertaOutputDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Boolean dispoStatus;
    private ResumoVendedorOfertaDTO vendedor;
}
