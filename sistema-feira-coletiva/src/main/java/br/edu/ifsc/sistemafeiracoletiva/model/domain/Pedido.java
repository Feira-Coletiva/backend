package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade JPA que representa a tabela "pedido" no banco de dados.
 */
@Entity
@Table(name = "pedidos")
@Data
@EqualsAndHashCode(of = {"id"})
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "qtd_produto")
    private Integer qtdTotalProdutos;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "id_participante", nullable = false)
    @JsonIgnoreProperties("pedidos")
    private Participante participante;
}
