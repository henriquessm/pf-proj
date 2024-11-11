package br.insper.projeto.historico.repository;

import br.insper.projeto.historico.model.Tarefa;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

}
