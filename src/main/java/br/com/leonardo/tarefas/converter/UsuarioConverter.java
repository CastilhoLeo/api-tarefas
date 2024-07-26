package br.com.leonardo.tarefas.converter;

import br.com.leonardo.tarefas.dto.UsuarioDTO;
import br.com.leonardo.tarefas.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioDTO usuarioDTO){
        return modelMapper.map(usuarioDTO, Usuario.class);
    }

    public UsuarioDTO toDto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}
