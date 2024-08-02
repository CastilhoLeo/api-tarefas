package br.com.leonardo.tarefas.builders;

import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;

import java.time.LocalDate;

public class TarefaBuilder {

    public static Tarefa criarTarefa(){
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDataCriacao(LocalDate.of(2024, 8, 01));
        tarefa.setTitulo("teste 1");
        tarefa.setDescricao("testeee 1");
        tarefa.setSituacao(Situacao.PENDENTE);
        tarefa.setDataVencimento(LocalDate.of(2024, 8, 10));

        return tarefa;
    }
}
