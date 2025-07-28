package br.edu.ifsc.sistemafeiracoletiva.dto;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.UnidadeDeMedida;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoSimplesOutputDTO {
    private Integer id;
    private String nome;
    private String categoria;
    private UnidadeDeMedida unidadeMedida;
    private Double medida; // Mantido como Double
}
