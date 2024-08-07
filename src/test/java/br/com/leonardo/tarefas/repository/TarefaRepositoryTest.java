package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.builders.TarefaBuilder;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private Pageable pageable;

    @BeforeAll
    public static void setup(@Autowired TarefaRepository tarefaRepository){
        Tarefa tarefa1 = TarefaBuilder.criarTarefa();
        tarefa1.setId(null);
        Tarefa tarefa2 = TarefaBuilder.criarTarefa();
        tarefa2.setId(null);
        tarefa2.setSituacao(Situacao.FINALIZADO);
        Tarefa tarefa3 = TarefaBuilder.criarTarefa();
        tarefa3.setId(null);
        tarefa2.setSituacao(Situacao.EM_ANDAMENTO);

        List<Tarefa> lista = new ArrayList<>();
        lista.add(tarefa1);
        lista.add(tarefa2);
        lista.add(tarefa3);

        tarefaRepository.saveAll(lista);
    }

    @Test
    public void findBySituacao(){
        Page<Tarefa> page = tarefaRepository.findBySituacao(Situacao.PENDENTE, pageable);


    }

}
