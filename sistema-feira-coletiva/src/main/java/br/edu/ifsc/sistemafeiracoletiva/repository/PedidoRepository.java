package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
