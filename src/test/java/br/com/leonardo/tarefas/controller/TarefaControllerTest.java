package br.com.leonardo.tarefas.controller;

import br.com.leonardo.tarefas.builders.TarefaDTOBuilder;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    public void pesquisaDinmica_deveRetornarTodasAsTarefas() throws Exception{

        TarefaDTO tarefaDTO1 = TarefaDTOBuilder.criarTarefaDTO();
        TarefaDTO tarefaDTO2 = TarefaDTOBuilder.criarTarefaDTO();
        tarefaDTO2.setTitulo("teste 2");

        Mockito.when(tarefaService.pesquisaDinamica(null, pageable))

        mockMvc.perform(MockMvcRequestBuilders.get("/tarefas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", Matchers.nullValue()));
    }


}
