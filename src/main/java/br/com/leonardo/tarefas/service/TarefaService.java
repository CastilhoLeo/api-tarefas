package br.com.leonardo.tarefas.service;

import br.com.leonardo.tarefas.converter.TarefaConverter;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import br.com.leonardo.tarefas.exception.TarefaNaoEncontradaException;
import br.com.leonardo.tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaConverter tarefaConverter;

    public TarefaDTO localizarPeloId(Long id){

        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(
                ()-> new TarefaNaoEncontradaException());

        return tarefaConverter.toDTO(tarefa);
    }

    public List<TarefaDTO> pesquisarTodos(){
        List<Tarefa> lista = tarefaRepository.findAll();

        List<TarefaDTO>listaDTO = lista.stream().map((tarefa)->tarefaConverter.toDTO(tarefa)).toList();

        return listaDTO;
    }

    public void deletarTarefa(Long id){
        tarefaRepository.deleteById(id);
    }

    public TarefaDTO cadastrarTarefa(TarefaDTO tarefaDTO){
        Tarefa tarefa = tarefaConverter.toEntity(tarefaDTO);
        tarefa.setDataCriacao(LocalDate.now());
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return tarefaConverter.toDTO(tarefaSalva);
    }

    public TarefaDTO editarTarefa(Long id, TarefaDTO tarefaEditada){
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(()-> new TarefaNaoEncontradaException());

        tarefa.setSituacao(tarefaEditada.getSituacao());
        tarefa.setDescricao(tarefaEditada.getDescricao());
        tarefa.setTitulo(tarefaEditada.getTitulo());
        tarefa.setDataVencimento(tarefaEditada.getDataVencimento());

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return tarefaConverter.toDTO(tarefaSalva);
    }

    public Page<TarefaDTO> pesquisaDinamica(LocalDate dataInicial, LocalDate dataFinal, Situacao situacao, Pageable pageable){

        if(dataInicial == null && dataFinal == null && situacao == null){
            return tarefaRepository.findAll(pageable).map(tarefa->tarefaConverter.toDTO(tarefa));
        }
        else {

            Page<Tarefa> page = tarefaRepository.findByDataVencimentoBetweenAndSituacao (dataInicial, dataInicial, situacao, pageable);
            Page<TarefaDTO> pageDTO = page.map(tarefa -> tarefaConverter.toDTO(tarefa));

            return pageDTO;
        }
    }
}
