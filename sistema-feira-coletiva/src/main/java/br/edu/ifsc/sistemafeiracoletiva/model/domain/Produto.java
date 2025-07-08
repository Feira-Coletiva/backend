package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Entity
@Data
@Table(name = "produtos")
@EqualsAndHashCode(of = {"id"})
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(name = "unidade_medida", nullable = false)
    @NotNull(message = "A unidade de medida é obrigatória")
    @Enumerated(EnumType.STRING)
    private Medida unidadeMedida = Medida.KG;

    @Column(name = "medida")
    @NotNull(message = "O medida é obrigatório")
    private Double medida;

    @Column(name = "preco")
    @NotNull(message = "O preço é obrigatório")
    private Double preco;

    @Column(name = "qtd_estoque")
    @NotNull(message = "O quantidade em estoque é obrigatório")
    private Integer qtdEstoque;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    @JsonIgnoreProperties("produtos")
    private Oferta oferta;
}
