package br.com.leonardo.tarefas.service;

import br.com.leonardo.tarefas.builders.TarefaBuilder;
import br.com.leonardo.tarefas.builders.TarefaDTOBuilder;
import br.com.leonardo.tarefas.converter.TarefaConverter;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import br.com.leonardo.tarefas.exception.TarefaNaoEncontradaException;
import br.com.leonardo.tarefas.repository.TarefaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaConverter tarefaConverter;

    @InjectMocks
    private TarefaService tarefaService;

    @Test
    public void localizarPeloId_deveRetornarUmaTarefa(){

        Tarefa tarefa = TarefaBuilder.criarTarefa();
        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();
        Mockito.when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        Mockito.when((tarefaConverter.toDTO(any(Tarefa.class)))).thenReturn(tarefaDTO);


        TarefaDTO resultado = tarefaService.localizarPeloId(1L);

        Assertions.assertEquals(resultado.getTitulo(), "teste 1");
        Assertions.assertEquals(resultado.getDescricao(), "testeee 1");
        Assertions.assertEquals(resultado.getSituacao(),  Situacao.PENDENTE);
        Assertions.assertEquals(resultado.getDataVencimento(), LocalDate.of(2024, 8, 10));
        Assertions.assertEquals(resultado.getClass(), TarefaDTO.class);
        Assertions.assertNotNull(resultado);

    }

    @Test
    public void localizarPeloId_DeveRetornarNull(){
        Tarefa tarefa = TarefaBuilder.criarTarefa();
        Mockito.when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        TarefaNaoEncontradaException exception = Assertions.assertThrows(
                TarefaNaoEncontradaException.class, ()-> tarefaService.localizarPeloId(1L));

        Assertions.assertEquals(exception.getMessage(), "Tarefa não encontrada");
    }

}
