package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade JPA que representa a tabela "clientes" no banco de dados.
 */
@Entity // Indica que esta classe será uma tabela no banco (entidade JPA)
@Table(name = "clientes") // Define explicitamente o nome da tabela no banco
@Data // Gera getters, setters, toString, equals e hashCode automaticamente
@EqualsAndHashCode(of = {"id"}) // Define que o hashCode será baseado apenas no campo "id"
public class Cliente {

    @Id // Define a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento (usado em MySQL, por exemplo)
    private Integer id;

    @Column(name = "nome") // personalizar os nomes das colunas
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(name = "email")
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(name = "telefone")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @Column(name = "senha")
    @JsonIgnore // Segurança no retorno do json, para que as senhas não fiquem visiveis
    @NotBlank(message = "A senha é obrigatória")
    private String senha;
}





