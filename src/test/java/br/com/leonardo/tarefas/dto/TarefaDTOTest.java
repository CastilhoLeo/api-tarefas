package br.com.leonardo.tarefas.dto;

import br.com.leonardo.tarefas.enums.Situacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;

@ActiveProfiles("test")
public class TarefaDTOTest {

    @Test
    public void criarTarefaDTO(){
        TarefaDTO tarefaDTO = new TarefaDTO(
                1L,
                "Estudar",
                "Estudar Java e Spring",
                LocalDate.of(2024,07,30),
                Situacao.EM_ANDAMENTO);

        Assertions.assertEquals(1L, tarefaDTO.getId());
        Assertions.assertEquals("Estudar", tarefaDTO.getTitulo());
        Assertions.assertEquals("Estudar Java e Spring", tarefaDTO.getDescricao());
        Assertions.assertEquals(LocalDate.of(2024,07,30), tarefaDTO.getDataVencimento());
        Assertions.assertEquals(Situacao.EM_ANDAMENTO, tarefaDTO.getSituacao());
    }



}
