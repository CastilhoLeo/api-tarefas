package br.com.leonardo.tarefas.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(RestExceptionHandler.class)
@ActiveProfiles("test")
public class RestExceptionHandlerTest {

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Test
    public void RestExceptionHandler_deveRetornarExcecao(){

        ResponseEntity<RestErrorMessage> response = restExceptionHandler.tarefaNaoEncontrada(new TarefaNaoEncontradaException());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getBody().getHttpStatus());
        Assertions.assertEquals("Tarefa não encontrada", response.getBody().getMessage());

    }
}
