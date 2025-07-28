package br.edu.ifsc.sistemafeiracoletiva.dto;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Vendedor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO usado para entrada de dados ao criar um Participante com seus Pedidos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteInputDTO {
    @NotNull(message = "O ID do cliente é obrigatório")
    private Integer idCliente;

    @NotNull(message = "O ID da publicação é obrigatório")
    private Integer idPublicacao;

    @Valid // Valida também a lista de PedidoInputDTOs
    @NotNull(message = "A lista de pedidos não pode ser nula")
    @Size(min = 1, message = "Deve haver pelo menos um pedido na participação")
    private List<PedidoInputDTO> pedidos;
}

