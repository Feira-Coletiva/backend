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
@Table(name = "publicacoes")
@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dt_final_exposicao", nullable = false)
    private LocalDate dtFinalExposicao;

    @Column(name = "dt_final_pagamento", nullable = false)
    private LocalDate dtFinalPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa", nullable = false)
    private Etapa etapa = Etapa.EXPOSICAO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local_de_retirada", nullable = false)
    private LocalDeRetirada localDeRetirada;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    // ✅ NOVO: Lista de participantes nesta publicação
    @OneToMany(mappedBy = "publicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("publicacao") // Evita recursão infinita JSON
    private List<Participante> participantes = new ArrayList<>();

    // Construtor para criação de nova publicação (mantido do PublicacaoService)
    public Publicacao(LocalDate dtFinalExposicao, LocalDate dtFinalPagamento, LocalDeRetirada localDeRetirada, Oferta oferta) {
        this.dtFinalExposicao = dtFinalExposicao;
        this.dtFinalPagamento = dtFinalPagamento;
        this.etapa = Etapa.EXPOSICAO; // Etapa inicial ao criar a publicação
        this.localDeRetirada = localDeRetirada;
        this.oferta = oferta;
        this.participantes = new ArrayList<>(); // Inicializa a lista
    }

    // Método para adicionar participante (se necessário, para gerenciar a lista)
    public void addParticipante(Participante participante) {
        this.participantes.add(participante);
        participante.setPublicacao(this); // Garante a relação bidirecional
    }
}
