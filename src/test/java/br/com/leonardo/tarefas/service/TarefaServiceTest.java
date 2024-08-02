package br.com.leonardo.tarefas.service;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import br.com.leonardo.tarefas.repository.TarefaRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeAll
    public static void setup(){
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setDataCriacao(LocalDate.of(2024, 8, 01));
        tarefa.setTitulo("teste 1");
        tarefa.setDescricao("testeee 1");
        tarefa.setSituacao(Situacao.PENDENTE);
        tarefa.setDataVencimento(LocalDate.of(2024, 8, 10));

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setTitulo("teste 1");
        tarefaDTO.setDescricao("testeee 1");
        tarefaDTO.setSituacao(Situacao.PENDENTE);
        tarefaDTO.setDataVencimento(LocalDate.of(2024, 8, 10));
    }

    @Test
    public void localizarPeloId_deveRetornarUmaTarefa(){

        Mockito.when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
    }

}
