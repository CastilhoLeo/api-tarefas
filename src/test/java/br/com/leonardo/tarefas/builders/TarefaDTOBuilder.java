package br.com.leonardo.tarefas.builders;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.enums.Situacao;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
public class TarefaDTOBuilder {

    public static TarefaDTO criarTarefaDTO() {
        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setTitulo("teste 1");
        tarefaDTO.setDescricao("testeee 1");
        tarefaDTO.setSituacao(Situacao.PENDENTE);
        tarefaDTO.setDataVencimento(LocalDate.of(2024, 8, 10));

        return tarefaDTO;
    }
}
