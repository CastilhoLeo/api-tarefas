package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;


public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query("select t from Tarefa t " +
            "where (t.dataVencimento>=:dataInicio or :dataInicio is null) " +
            "and (t.dataVencimento <=:dataFim or :dataFim is null) " +
            "and t.situacao = :situacao")
    Page<Tarefa> pesquisaDinamica (LocalDate dataInicio, LocalDate dataFim, Situacao situacao, Pageable pageable);
}
