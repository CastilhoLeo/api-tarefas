package br.com.leonardo.tarefas.service;

import br.com.leonardo.tarefas.converter.UsuarioConverter;
import br.com.leonardo.tarefas.dto.UsuarioDTO;
import br.com.leonardo.tarefas.entity.Usuario;
import br.com.leonardo.tarefas.repository.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioConverter converter;

    public Usuario criarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = converter.toEntity(usuarioDTO);
       return repository.save(usuario);
    }

    public UsuarioDTO editarUsuario(Long id, UsuarioDTO usuarioEditadoDTO){
        Usuario usuario = repository.findById(id)
                .orElseThrow(()->new ValidationException("Usuário não localizado"));

        usuario.setNome(usuarioEditadoDTO.getNome());
        usuario.setCpf(usuarioEditadoDTO.getCpf());

        Usuario usuarioSalvo = repository.save(usuario);

        return converter.toDto(usuarioSalvo);
    }

    public void deletarUsuario(Long id){
        repository.deleteById(id);
    }

    public List<UsuarioDTO> pesquisarTodos(){
        List<Usuario> lista = repository.findAll();

        return lista.stream().map((usuario)-> converter.toDto(usuario)).toList();
    }

    public UsuarioDTO localizarPeloId(Long id){
        Usuario usuario = repository.findById(id)
                .orElseThrow(()-> new ValidationException("Usuario não localizado"));

        return converter.toDto(usuario);
    }
}
