package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado para saída de dados junto da Oferta (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 * Oferta -> info vendedor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumoVendedorOfertaDTO {
    private Integer id;
    private String telefone;
    private String chavePix;
}
