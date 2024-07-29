package br.com.leonardo.tarefas.enums;

public enum Situacao {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private String situacao;

    Situacao(String situacao){
        this.situacao = situacao;
    }

    String getSituacao(){
        return this.situacao;
    }

}
