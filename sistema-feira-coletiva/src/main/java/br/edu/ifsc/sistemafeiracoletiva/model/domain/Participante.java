package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade JPA que representa a tabela "participante" no banco de dados.
 */
@Entity
@Table(name = "participantes")
@Data
@EqualsAndHashCode(of = {"id"})
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "qtd_total_produtos")
    private Integer qtdTotalProdutos;

    @Column(name = "status_pago")
    private Boolean statusPago = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_publicacao")
    private Publicacao publicacao;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("participante")
    private List<Pedido> pedidos = new ArrayList<>();

}
