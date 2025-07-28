package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Entidade JPA que representa a tabela "produtos" no banco de dados.
 */
@Entity
@Table(name = "produtos")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_medida", nullable = false)
    private UnidadeDeMedida unidadeMedida;

    @Column(name = "medida", nullable = false)
    private Double medida; // Mantido como Double, pode ser BigDecimal se a precisão for crítica para a medida

    @Column(name = "preco", nullable = false, precision = 10, scale = 2) // ✅ BigDecimal com precisão
    private BigDecimal preco;

    @Column(name = "qtd_estoque", nullable = false)
    private Integer qtdEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    public Produto(String nome, Categoria categoria, UnidadeDeMedida unidadeMedida, Double medida, BigDecimal preco, Integer qtdEstoque) {
        this.nome = nome;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
        this.medida = medida;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
    }
}
