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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
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

    @Test
    public void cadastrarTarefa_DeveCadastarUmTarefa(){
        Tarefa tarefa = TarefaBuilder.criarTarefa();
        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();

        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when((tarefaConverter.toDTO(any(Tarefa.class)))).thenReturn(tarefaDTO);
        Mockito.when((tarefaConverter.toEntity(any(TarefaDTO.class)))).thenReturn(tarefa);

        TarefaDTO resultado = tarefaService.cadastrarTarefa(tarefaDTO);

        Assertions.assertEquals(resultado.getClass(), TarefaDTO.class);
        Assertions.assertEquals(resultado.getTitulo(), "teste 1");
        Assertions.assertEquals(resultado.getDescricao(), "testeee 1");
        Assertions.assertEquals(resultado.getSituacao(),  Situacao.PENDENTE);
        Assertions.assertEquals(resultado.getDataVencimento(), LocalDate.of(2024, 8, 10));
        Assertions.assertNotNull(resultado);

    }

    @Test
    public void editarTarefa_DeveRetornarTarefaEditado(){
        Tarefa tarefa = TarefaBuilder.criarTarefa();
        TarefaDTO tarefaEditada = TarefaDTOBuilder.criarTarefaDTO();
        tarefaEditada.setDescricao("teste edição");

        Mockito.when(tarefaRepository.findById(anyLong())).thenReturn(Optional.of(tarefa));
        Mockito.when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when((tarefaConverter.toDTO(any(Tarefa.class)))).thenReturn(tarefaEditada);
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);


        TarefaDTO tarefaDTOEditado = tarefaService.editarTarefa(1L, tarefaEditada);

        Assertions.assertEquals(tarefaDTOEditado.getClass(), tarefaEditada.getClass());
        Assertions.assertNotNull(tarefaDTOEditado);
        Assertions.assertEquals(tarefaDTOEditado.getTitulo(), "teste 1");
        Assertions.assertEquals(tarefaDTOEditado.getSituacao(), Situacao.PENDENTE);
        Assertions.assertEquals(tarefaDTOEditado.getDataVencimento(), LocalDate.of(2024, 8, 10));
        Assertions.assertEquals(tarefaDTOEditado.getDescricao(), "teste edição");

        Mockito.verify(tarefaRepository, Mockito.times(1)).save(captor.capture());
        Tarefa tarefa1 = captor.getValue();
        Assertions.assertEquals(tarefa1.getDescricao(), "teste edição");

    }

    @Test
    public void  editarTarefa_deveRetornarException(){
        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();
        Mockito.when(tarefaRepository.findById(anyLong())).thenReturn(Optional.empty());

        TarefaNaoEncontradaException exception = Assertions.assertThrows(
                TarefaNaoEncontradaException.class, ()-> tarefaService.editarTarefa(1L, tarefaDTO));

        Assertions.assertEquals(exception.getMessage(), "Tarefa não encontrada");
        Mockito.verify(tarefaRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    public void deletarTarefa_DeveDeletarUmaTarefa(){
        tarefaService.deletarTarefa(1L);

        Mockito.verify(tarefaRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void pesquisaDinamica_DeveRetornarTodos(){

        Pageable pageable = PageRequest.of(1, 10);

        List<Tarefa> listaTarefas = new ArrayList<>();

        Tarefa tarefa1 = TarefaBuilder.criarTarefa();
        Tarefa tarefa2 = TarefaBuilder.criarTarefa();
        tarefa2.setTitulo("teste 2");
        Tarefa tarefa3 = TarefaBuilder.criarTarefa();
        tarefa3.setTitulo("teste 3");

        listaTarefas.add(tarefa1);
        listaTarefas.add(tarefa2);
        listaTarefas.add(tarefa3);

        TarefaDTO tarefaDTO1 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO2 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO3 = TarefaDTOBuilder.criarTarefaDTO();

        Page<Tarefa> pageTarefas = new PageImpl<>(listaTarefas);


        Mockito.when(tarefaRepository.localizarTodos(any(Pageable.class))).thenReturn(pageTarefas);
        Mockito.when(tarefaConverter.toDTO(tarefa1)).thenReturn(tarefaDTO1);
        Mockito.when(tarefaConverter.toDTO(tarefa2)).thenReturn(tarefaDTO2);
        Mockito.when(tarefaConverter.toDTO(tarefa3)).thenReturn(tarefaDTO3);

        Page<TarefaDTO> pageCriado = tarefaService.pesquisaDinamica( null, pageable);

        Mockito.verify(tarefaRepository, Mockito.times(1)).localizarTodos(pageable);
        Assertions.assertEquals(pageCriado.getSize(), 3);
        Assertions.assertEquals(pageCriado.getContent().get(0).getClass(), TarefaDTO.class);

    }

    @Test
    public void pesquisaDinamica_DevePesquisaDinamica(){

        Pageable pageable = PageRequest.of(1, 10);

        List<Tarefa> listaTarefas = new ArrayList<>();

        Tarefa tarefa1 = TarefaBuilder.criarTarefa();
        Tarefa tarefa2 = TarefaBuilder.criarTarefa();
        tarefa2.setTitulo("teste 2");
        Tarefa tarefa3 = TarefaBuilder.criarTarefa();
        tarefa3.setTitulo("teste 3");

        listaTarefas.add(tarefa1);
        listaTarefas.add(tarefa2);
        listaTarefas.add(tarefa3);

        TarefaDTO tarefaDTO1 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO2 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO3 = TarefaDTOBuilder.criarTarefaDTO();

        Page<Tarefa> pageTarefas = new PageImpl<>(listaTarefas);


        Mockito.when(tarefaRepository.findBySituacaoOrderByDataVencimentoAsc(any(Situacao.class), any(Pageable.class))).thenReturn(pageTarefas);
        Mockito.when(tarefaConverter.toDTO(tarefa1)).thenReturn(tarefaDTO1);
        Mockito.when(tarefaConverter.toDTO(tarefa2)).thenReturn(tarefaDTO2);
        Mockito.when(tarefaConverter.toDTO(tarefa3)).thenReturn(tarefaDTO3);

        Page<TarefaDTO> pageCriado = tarefaService.pesquisaDinamica( Situacao.PENDENTE, pageable);

        Mockito.verify(tarefaRepository, Mockito.times(1)).findBySituacaoOrderByDataVencimentoAsc(Situacao.PENDENTE, pageable);
        Assertions.assertEquals(pageCriado.getSize(), 3);
        Assertions.assertEquals(pageCriado.getContent().get(0).getClass(), TarefaDTO.class);

    }
}
