package br.com.leonardo.tarefas.controller;

import br.com.leonardo.tarefas.builders.TarefaDTOBuilder;
import br.com.leonardo.tarefas.dto.TarefaDTO;
import br.com.leonardo.tarefas.enums.Situacao;
import br.com.leonardo.tarefas.service.TarefaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

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


    }




}
