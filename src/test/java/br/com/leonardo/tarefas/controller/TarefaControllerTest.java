package br.com.leonardo.tarefas.controller;

import br.com.leonardo.tarefas.builders.TarefaDTOBuilder;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.enums.Situacao;
import br.com.leonardo.tarefas.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TarefaController.class)
@ActiveProfiles("test")
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TarefaService tarefaService;

    @Test
    public void cadastrarTarefa () throws Exception {

        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();
        Mockito.when(tarefaService.cadastrarTarefa(ArgumentMatchers.any(TarefaDTO.class))).thenReturn(tarefaDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/tarefas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefaDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.nullValue()))
                .andExpect(jsonPath("$.titulo", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.situacao", Matchers.is("PENDENTE")));

        Mockito.verify(tarefaService, Mockito.times(1)).cadastrarTarefa(tarefaDTO);
    }

    @Test
    public void editarTarefa() throws Exception {

        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();
        Mockito.when(tarefaService.editarTarefa(ArgumentMatchers.anyLong(), ArgumentMatchers.any(TarefaDTO.class)))
                .thenReturn(tarefaDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/tarefas/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tarefaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.nullValue()))
                .andExpect(jsonPath("$.titulo", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.situacao", Matchers.is("PENDENTE")));

        Mockito.verify(tarefaService, Mockito.times(1)).editarTarefa(1L, tarefaDTO);
    }

    @Test
    public void deletarTarefa() throws Exception {

        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();

        mockMvc.perform(MockMvcRequestBuilders.delete("/tarefas/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        Mockito.verify(tarefaService, Mockito.times(1)).deletarTarefa(1L);

    }

    @Test
    public void pesquisaDinmica_devePesquisarComSituacaoNull() throws Exception{

        Pageable pageable = PageRequest.of(1,20);

        TarefaDTO tarefaDTO1 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO2 = TarefaDTOBuilder.criarTarefaDTO();
        tarefaDTO2.setTitulo("teste 2");

        List<TarefaDTO> lista = new ArrayList<>();
        lista.add(tarefaDTO1);
        lista.add(tarefaDTO2);

        Page<TarefaDTO> page = new PageImpl<>(lista);

        Mockito.when(tarefaService.pesquisaDinamica(null, pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas")
                        .param("situacao", "")
                        .param("page", "1")
                        .param("size", "20"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", Matchers.nullValue()))
                .andExpect(jsonPath("$.content[0].titulo", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.content[0].descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.content[0].dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.content[0].situacao", Matchers.is("PENDENTE")))
                .andExpect(jsonPath("$.content[1].id", Matchers.nullValue()))
                .andExpect(jsonPath("$.content[1].titulo", Matchers.is("teste 2")))
                .andExpect(jsonPath("$.content[1].descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.content[1].dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.content[1].situacao", Matchers.is("PENDENTE")));

        Mockito.verify(tarefaService, Mockito.times(1))
                .pesquisaDinamica(null, pageable);
    }


    @Test
    public void pesquisaDinmica_devePesquisarComFiltroDeSituacao() throws Exception{

        Pageable pageable = PageRequest.of(1,20);

        TarefaDTO tarefaDTO1 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO2 = TarefaDTOBuilder.criarTarefaDTO();
        tarefaDTO2.setTitulo("teste 2");

        List<TarefaDTO> lista = new ArrayList<>();
        lista.add(tarefaDTO1);
        lista.add(tarefaDTO2);

        Page<TarefaDTO> page = new PageImpl<>(lista);

        Mockito.when(tarefaService.pesquisaDinamica(Situacao.PENDENTE, pageable)).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas")
                        .param("situacao", "PENDENTE")
                        .param("page", "1")
                        .param("size", "20"))

                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", Matchers.nullValue()))
                .andExpect(jsonPath("$.content[0].titulo", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.content[0].descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.content[0].dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.content[0].situacao", Matchers.is("PENDENTE")))
                .andExpect(jsonPath("$.content[1].id", Matchers.nullValue()))
                .andExpect(jsonPath("$.content[1].titulo", Matchers.is("teste 2")))
                .andExpect(jsonPath("$.content[1].descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.content[1].dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.content[1].situacao", Matchers.is("PENDENTE")));

        Mockito.verify(tarefaService, Mockito.times(1))
                .pesquisaDinamica(Situacao.PENDENTE, pageable);
    }

    @Test
    public void pesquisarPeloId() throws Exception {

        TarefaDTO tarefaDTO = TarefaDTOBuilder.criarTarefaDTO();
        Mockito.when(tarefaService.localizarPeloId(ArgumentMatchers.anyLong())).thenReturn(tarefaDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas/{id}",1L ))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.nullValue()))
                .andExpect(jsonPath("$.titulo", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.descricao", Matchers.is("testeee 1")))
                .andExpect(jsonPath("$.dataVencimento", Matchers.is("2024-08-10")))
                .andExpect(jsonPath("$.situacao", Matchers.is("PENDENTE")));


        Mockito.verify(tarefaService, Mockito.times(1)).localizarPeloId(1L);

    }


}
