package br.insper.projeto.historico.repository;

import br.insper.projeto.historico.model.Historico;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer> {
    List<Historico> findByEmail(String email);

}
