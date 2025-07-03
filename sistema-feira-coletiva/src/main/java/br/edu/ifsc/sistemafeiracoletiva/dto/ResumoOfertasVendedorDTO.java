package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado para saída de dados junto da Vendedor (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 * Vendedor -> info ofertas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumoOfertasVendedorDTO {
    private Integer id;
    private String titulo;
    private Boolean dispoStatus;
}
