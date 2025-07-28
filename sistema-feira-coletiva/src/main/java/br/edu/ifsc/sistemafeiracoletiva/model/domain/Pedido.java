package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade JPA que representa um item de pedido dentro de uma participação.
 */
@Entity
@Table(name = "pedidos")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor // Construtor sem argumentos para JPA
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ✅ NOVO: Relacionamento direto com o Produto que foi pedido
    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY para evitar carregamento desnecessário
    @JoinColumn(name = "id_produto", nullable = false)
    private Produto produto;

    @Column(name = "qtd_produto", nullable = false)
    private Integer qtdProdutos;

    // ✅ NOVO: Preço unitário do produto NO MOMENTO do pedido
    @Column(name = "preco_unitario_no_pedido", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitarioNoPedido;

    // ✅ AGORA: 'valor' é o valor total para este item do pedido (qtd * preco_unitario_no_pedido)
    @Column(name = "valor_total_item", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotalItem;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY
    @JoinColumn(name = "id_participante", nullable = false)
    @JsonIgnoreProperties("pedidos") // Evita recursão infinita JSON
    private Participante participante;

    // Construtor para facilitar a criação de um pedido
    public Pedido(Produto produto, Integer qtdProdutos, BigDecimal precoUnitarioNoPedido, Participante participante) {
        this.produto = produto;
        this.qtdProdutos = qtdProdutos;
        this.precoUnitarioNoPedido = precoUnitarioNoPedido;
        this.valorTotalItem = precoUnitarioNoPedido.multiply(BigDecimal.valueOf(qtdProdutos));
        this.participante = participante;
    }
}
