package br.com.leonardo.tarefas.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestErrorMessage {

    private HttpStatus httpStatus;
    private String message;
}
