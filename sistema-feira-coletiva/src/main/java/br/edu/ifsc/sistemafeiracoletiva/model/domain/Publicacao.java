package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade JPA que representa a tabela "publicacao" no banco de dados.
 */
@Entity
@Data
@Table(name = "publicacoes")
@EqualsAndHashCode(of = {"id"})
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dt_final_exposicao", nullable = false)
    @NotNull(message = "A data final de exposição é obrigatória")
    private LocalDate dtFinalExposicao;

    @Column(name = "dt_final_pagamento", nullable = false)
    @NotNull(message = "A data final de pagamento é obrigatória")
    private LocalDate dtFinalPagamento;

    @Column(name = "etapa", nullable = false)
    @NotNull(message = "A etapa é obrigatória")
    @Enumerated(EnumType.STRING)
    private Etapa etapa = Etapa.EXPOSICAO;

    @ManyToOne(fetch = FetchType.EAGER) // ✅ Usando EAGER aqui para garantir que o LocalDeRetirada seja carregado junto
    @JoinColumn(name = "id_local_de_retirada", nullable = false)
    private LocalDeRetirada localDeRetirada;

    @ManyToOne(fetch = FetchType.EAGER) // ✅ Usando EAGER aqui para garantir que a Oferta seja carregada junto
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("publicacao")
    private List<Participante> participantes = new ArrayList<>();

    public Publicacao() {}

    // Construtor para criar uma nova publicação com a etapa inicial EXPOSICAO
    public Publicacao(LocalDate dtFinalExposicao, LocalDate dtFinalPagamento, LocalDeRetirada localDeRetirada, Oferta oferta) {
        this.dtFinalExposicao = dtFinalExposicao;
        this.dtFinalPagamento = dtFinalPagamento;
        this.localDeRetirada = localDeRetirada;
        this.oferta = oferta;
        this.etapa = Etapa.EXPOSICAO;
        this.participantes = new ArrayList<>();
    }
}
