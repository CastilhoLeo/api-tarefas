package br.com.leonardo.tarefas.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<RestErrorMessage> tarefaNaoEncontrada(TarefaNaoEncontradaException exception){
        RestErrorMessage message = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestErrorMessage> constraintViolation(ConstraintViolationException exception){
        String message = exception.getConstraintViolations().stream().map(ex ->ex.getMessage()).collect(Collectors.joining("\n"));
        RestErrorMessage restErrorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restErrorMessage);
    }
}
