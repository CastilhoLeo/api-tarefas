package br.com.leonardo.tarefas.service;

import br.com.leonardo.tarefas.converter.TarefaConverter;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.repository.TarefaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaConverter tarefaConverter;

    public TarefaDTO localizarPeloId(Long id){

        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(
                ()-> new ValidationException("Tarefa não encontrada"));

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
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return tarefaConverter.toDTO(tarefaSalva);
    }

    public TarefaDTO editarTarefa(Long id, TarefaDTO tarefaEditada){
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(()-> new ValidationException("Tarefa não localizada"));

        tarefa.setSituacao(tarefaEditada.getSituacao());
        tarefa.setDescricao(tarefaEditada.getDescricao());
        tarefa.setTitulo(tarefaEditada.getTitulo());
        tarefa.setDataCriacao(tarefaEditada.getDataCriacao());
        tarefa.setDataVencimento(tarefaEditada.getDataVencimento());

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);

        return tarefaConverter.toDTO(tarefaSalva);
    }
}
