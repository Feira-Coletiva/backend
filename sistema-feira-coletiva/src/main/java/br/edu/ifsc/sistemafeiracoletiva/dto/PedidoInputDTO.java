package br.edu.ifsc.sistemafeiracoletiva.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO usado para entrada de dados ao criar ou atualizar um Pedido.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoInputDTO {
    @NotNull(message = "O ID do produto é obrigatório")
    private Integer idProduto;

    @NotNull(message = "A quantidade do produto é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser de pelo menos 1")
    private Integer qtdProdutos;
}
