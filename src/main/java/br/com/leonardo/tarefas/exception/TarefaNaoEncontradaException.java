package br.com.leonardo.tarefas.exception;

import br.com.leonardo.tarefas.entity.Tarefa;

public class TarefaNaoEncontradaException extends RuntimeException{
    public TarefaNaoEncontradaException(){
        super("Tarefa não encontrada!");
    }

    public TarefaNaoEncontradaException(String message){
        super(message);
    }
}
