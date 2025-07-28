package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade JPA que representa a tabela "participante" no banco de dados.
 */
@Entity
@Table(name = "participantes")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_total", precision = 10, scale = 2) // Usar BigDecimal para valores monetários
    private BigDecimal valorTotal;

    @Column(name = "qtd_total_produtos")
    private Integer qtdTotalProdutos;

    @Column(name = "status_pago")
    private Boolean statusPago = Boolean.FALSE; // ✅ Sugestão: Alterar para FALSE se o pagamento é posterior

    @Column(name = "data_participacao") // ✅ Sugestão: Adicionar campo de data/hora
    private LocalDateTime dataParticipacao;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY) // Usar LAZY
    @JoinColumn(name = "id_publicacao")
    private Publicacao publicacao;

    @OneToMany(mappedBy = "participante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("participante")
    private List<Pedido> pedidos = new ArrayList<>();

    // Construtor para facilitar a criação de um Participante
    public Participante(Cliente cliente, Publicacao publicacao) {
        this.cliente = cliente;
        this.publicacao = publicacao;
        this.valorTotal = BigDecimal.ZERO; // Inicia com zero
        this.qtdTotalProdutos = 0; // Inicia com zero
        this.statusPago = Boolean.FALSE; // Assume que o pagamento não foi feito ainda
        this.dataParticipacao = LocalDateTime.now(); // Data e hora atual da participação
        this.pedidos = new ArrayList<>();
    }

    // Método para adicionar pedidos e calcular totais
    public void addPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setParticipante(this); // Garante a relação bidirecional
        this.valorTotal = this.valorTotal.add(pedido.getValorTotalItem());
        this.qtdTotalProdutos += pedido.getQtdProdutos();
    }
}
