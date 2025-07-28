package br.edu.ifsc.sistemafeiracoletiva.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteResumoPublicacaoOutputDTO {
    private Integer id;
    private BigDecimal valorTotal;
    private Integer qtdTotalProdutos;
    private Boolean statusPago;
    private LocalDateTime dataParticipacao;
    private ClienteOutputDTO cliente; // Reutiliza ClienteOutputDTO
    private List<PedidoOutputDTO> pedidos; // Inclui os pedidos completos
}
