package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "qtd_estoque_total")
    @NotNull(message = "A quantidade em estoque total é obrigatório")
    private Integer qtdEstoqueTotal;

    @Column(name = "status_disponibilidade")
    private Boolean statusDisponibilidade = Boolean.TRUE;

    @ManyToOne // Indica que muitas Ofertas podem estar ligadas a um único Vendedor (N:1).
    @JoinColumn(name="id_vendedor") // Define o nome da coluna de chave estrangeira no banco de dados que liga a Oferta ao Vendedor.
    @JsonIgnoreProperties("ofertas")
    private Vendedor vendedor;

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)    // CascadeType.ALL: persistir/remover produtos automaticamente junto com a oferta.
    @JsonIgnoreProperties("oferta")                                                     // orphanRemoval = true: produtos removidos da lista também são removidos do banco.
    private List<Produto> produtos = new ArrayList<>();

    // ✅ Calcula e atualiza o estoque total da oferta
    public void calcularQtdEstoqueTotal() {
        this.qtdEstoqueTotal = produtos.stream()
                .mapToInt(Produto::getQtdEstoque)
                .sum();
    }

    // ✅ Adiciona produto e recalcula estoque total
    public void addProduto(Produto produto) {
        produto.setOferta(this);
        produtos.add(produto);
        calcularQtdEstoqueTotal();
    }

    // ✅ Remove produto e recalcula estoque total
    public void removerProduto(Produto produto) {
        produtos.remove(produto);
        calcularQtdEstoqueTotal();
    }
}
