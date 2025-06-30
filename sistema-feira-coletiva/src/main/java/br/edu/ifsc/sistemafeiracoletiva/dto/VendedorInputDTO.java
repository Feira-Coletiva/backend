package br.edu.ifsc.sistemafeiracoletiva.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO usado para entrada de dados (POST e PUT).
 * O cliente envia apenas esses campos no JSON da requisição.
 */
@Data
public class VendedorInputDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Senha é obrigatória")
    private String senha;

    @NotBlank(message = "O RG é obrigatório")
    private Integer rg;

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    @NotBlank(message = "A região de atuação é obrigatória")
    private String regDeAtuacao;

    @NotBlank(message = "A chave pix é obrigatória")
    private String chavePix;
}

