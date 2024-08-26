package br.com.leonardo.tarefas.repository;

import br.com.leonardo.tarefas.builders.TarefaBuilder;
import br.com.leonardo.tarefas.entity.Tarefa;
import br.com.leonardo.tarefas.enums.Situacao;
import org.junit.jupiter.api.Assertions;
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

    private Pageable pageable;

    @BeforeAll
    public static void setup(@Autowired TarefaRepository tarefaRepository){
        Tarefa tarefa1 = TarefaBuilder.criarTarefa();
        tarefa1.setId(1L);
        Tarefa tarefa2 = TarefaBuilder.criarTarefa();
        tarefa2.setId(2L);
        tarefa2.setSituacao(Situacao.FINALIZADO);
        Tarefa tarefa3 = TarefaBuilder.criarTarefa();


        List<Tarefa> lista = new ArrayList<>();
        lista.add(tarefa1);
        lista.add(tarefa2);


        tarefaRepository.saveAll(lista);
    }

    @Test
    public void findBySituacao(){
        Page<Tarefa> page = this.tarefaRepository.findBySituacaoOrderByDataVencimentoAsc(Situacao.PENDENTE, pageable);

        Assertions.assertEquals(page.getSize(), 1);
        Assertions.assertEquals(page.getContent().get(0).getId(), 1L);

    }

}
