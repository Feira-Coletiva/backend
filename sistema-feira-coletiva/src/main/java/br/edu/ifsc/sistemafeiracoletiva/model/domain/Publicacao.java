package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "publicacoes")
@EqualsAndHashCode(of = {"id"})
public class Publicacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dt_final_exposicao")
    @NotBlank(message = "O data final de exposição é obrigatório")
    private LocalDate dtFinalExposicao;

    @Column(name = "dt_final_pagamento")
    @NotBlank(message = "O data final de pagamento é obrigatório")
    private LocalDate dtFinalPagamento;

    @Column(name = "etapa", nullable = false)
    @NotNull(message = "A etapa é obrigatória")
    @Enumerated(EnumType.STRING)
    private Etapa etapa = Etapa.INCOMPLETO;

    @ManyToOne
    @JoinColumn(name = "id_local_de_retirada")
    private LocalDeRetirada localDeRetirada;

    @ManyToOne
    @JoinColumn(name = "id_oferta")
    private Oferta oferta;
}
