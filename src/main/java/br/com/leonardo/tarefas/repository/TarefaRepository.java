package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

//    @Query("select t from Tarefa t " +
//            "where t.dataVencimento between :dataInicial and :dataFinal " +
//            "and t.situacao = :situacao")
//    Page<Tarefa> pesquisaDinamica (LocalDate dataInicial, LocalDate dataFinal, Situacao situacao, Pageable pageable);


    Page<Tarefa> findByDataVencimentoBetweenAndSituacao (LocalDate dataInicial, LocalDate dataFinal, Situacao situacao, Pageable pageable);
}
