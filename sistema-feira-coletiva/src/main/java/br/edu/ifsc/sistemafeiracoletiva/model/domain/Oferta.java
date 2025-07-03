package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade JPA que representa a tabela "ofertas" no banco de dados.
 */
@Entity
@Table(name = "ofertas")
@Data
@EqualsAndHashCode(of = {"id"})
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo")
    @NotBlank(message = "O titulo é obrigatório")
    @Size(min = 3, max = 100, message = "O titulo deve ter entre 3 e 100 caracteres")
    private String titulo;

    @Column(name = "descricao")
    @NotBlank(message = "O descrição é obrigatório")
    @Size(min = 10, max = 250, message = "A descrição deve ter entre 10 e 250 caracteres")
    private String descricao;

    @Column(nullable = false)
    @NotBlank(message = "A disponibilidade é obrigatório")
    private Boolean dispStatus =  Boolean.TRUE;

    @ManyToOne // Indica que muitas Ofertas podem estar ligadas a um único Vendedor (N:1).
    @JoinColumn(name="id_vendedor") // Define o nome da coluna de chave estrangeira no banco de dados que liga a Oferta ao Vendedor.
    @JsonIgnoreProperties("ofertas")
    private Vendedor vendedor;
}
