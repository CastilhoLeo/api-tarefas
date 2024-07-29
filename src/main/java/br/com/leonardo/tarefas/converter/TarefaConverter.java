package br.com.leonardo.tarefas.converter;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TarefaConverter {

    @Autowired
    private ModelMapper modelMapper;


        public TarefaDTO toDTO(Tarefa tarefa){

            return modelMapper.map(tarefa, TarefaDTO.class);


        }

        public Tarefa toEntity(TarefaDTO tarefaDTO){
            return modelMapper.map(tarefaDTO, Tarefa.class);
        }

}
