package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "email")
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

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

    @Column(name = "cep")
    @NotBlank(message = "O CEP é obrigatório")
    private String cep;

    @Column(name = "chave_pix")
    @NotBlank(message = "A chave pix é obrigatória")
    private String chavePix;

    // Indica que um Vendedor pode ter muitas Ofertas (relação 1:N).
    @OneToMany(mappedBy="vendedor")     // mappedBy="vendedor" significa que o lado dono da relação está na entidade Oferta, no atributo vendedor.
    // Ou seja, a tabela de Oferta possui a chave estrangeira (FK), não a de Vendedor.
    @JsonIgnoreProperties("vendedor") // Serve para ignorar a propriedade vendedor dentro de cada Oferta ao serializar em JSON.
    private List<Oferta> ofertas = new ArrayList<>();

    //    @JsonIgnoreProperties("vendedores")
    //    Evita recursão infinita ao serializar em JSON:
    //      Vendedor → lista de Oferta → cada Oferta tem Vendedor → infinito.
}
