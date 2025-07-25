package br.edu.ifsc.sistemafeiracoletiva.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "locais_de_retirada")
@EqualsAndHashCode(of = {"id"})
public class LocalDeRetirada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(name = "cep")
    @NotBlank(message = "O cep é obrigatório")
    private String cep;
}
