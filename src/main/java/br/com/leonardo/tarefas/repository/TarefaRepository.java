package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
