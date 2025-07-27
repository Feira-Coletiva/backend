package br.edu.ifsc.sistemafeiracoletiva.repository;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipanteRepository extends JpaRepository<Participante, Integer> {

}
