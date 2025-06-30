package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

//    @Column(name = "unidade_medida")
//    @NotBlank(message = "O unidade de medida é obrigatório")
//    private String unidadeMedida;

    @Column(name = "medida")
    @NotBlank(message = "O medida é obrigatório")
    private Double medida;

    @Column(name = "preco")
    @NotBlank(message = "O preço é obrigatório")
    private Double preco;

    @Column(name = "qtd_estoque")
    @NotBlank(message = "O quantidade em estoque é obrigatório")
    private Integer qtdEstoque;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

}
