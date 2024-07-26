package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
