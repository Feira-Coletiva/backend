package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO usado para saída de dados vendedor join oferta (GET, POST response).
 * O backend envia apenas esses campos ao frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendedorSuasOfertasOutputDTO {
    private Integer id;
    private String nome;
    private List<ResumoOfertasVendedorDTO> ofertas ;
}
