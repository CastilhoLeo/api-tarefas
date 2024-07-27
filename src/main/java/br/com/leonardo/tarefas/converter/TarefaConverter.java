package br.com.leonardo.tarefas.converter;

import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.dto.UsuarioDTO;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TarefaConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioConverter usuarioConverter;

        public TarefaDTO toDTO(Tarefa tarefa){

            UsuarioDTO usuarioDTO = usuarioConverter.toDto(tarefa.getUsuario());
            TarefaDTO tarefaDTO = modelMapper.map(tarefa, TarefaDTO.class);
            tarefaDTO.setUsuarioDTO(usuarioDTO);

            return tarefaDTO;
        }

        public Tarefa toEntity(TarefaDTO tarefaDTO){
            Usuario usuario = usuarioConverter.toEntity(tarefaDTO.getUsuarioDTO());
            Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);
            tarefa.setUsuario(usuario);

            return tarefa;
        }

}
