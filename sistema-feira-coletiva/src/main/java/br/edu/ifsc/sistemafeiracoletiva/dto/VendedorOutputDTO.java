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
public class VendedorOutputDTO {

    private Integer id;
    private String nome;
    private String telefone;
    private String regDeAtuacao;
    private String chavePix;
}

