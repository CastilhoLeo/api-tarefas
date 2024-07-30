package br.com.leonardo.tarefas.exception;


public class TarefaNaoEncontradaException extends RuntimeException{


   public TarefaNaoEncontradaException(){
       super("Tarefa não encontrada");
    }

    public TarefaNaoEncontradaException(String message){
       super(message);
    }
}
