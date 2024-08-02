package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>, JpaSpecificationExecutor<Tarefa> {


    Page<Tarefa> findBySituacao (Situacao situacao, Pageable pageable);
}
