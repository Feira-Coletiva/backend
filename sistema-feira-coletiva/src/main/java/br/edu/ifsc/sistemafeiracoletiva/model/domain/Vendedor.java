package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade JPA que representa a tabela "vendedor" no banco de dados.
 */
@Entity
@Table(name = "vendedores")
@Data
@EqualsAndHashCode(of = {"id"})
public class Vendedor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(name = "telefone")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Column(name = "senha")
    @JsonIgnore
    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @Column(name = "rg")
    @NotBlank(message = "O RG é obrigatório")
    private Integer rg;

    @Column(name = "endereco")
    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    @Column(name = "reg_de_atuacao")
    @NotBlank(message = "A região de atuação é obrigatória")
    private String regDeAtuacao;

    @Column(name = "chave_pix")
    @NotBlank(message = "A chave pix é obrigatória")
    private String chavePix;
}
