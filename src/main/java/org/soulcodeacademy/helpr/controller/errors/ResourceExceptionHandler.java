package org.soulcodeacademy.helpr.controller.errors;

import org.soulcodeacademy.helpr.services.errors.ParametrosInsuficientesError;
import org.soulcodeacademy.helpr.services.errors.RecursoNaoEncontradoError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

// Quando acontece um erro, esta classe decide como retornar a mensagem para o cliente
@ControllerAdvice // capacidade de gerenciar os erros que acontecem
public class ResourceExceptionHandler {
    @ExceptionHandler(RecursoNaoEncontradoError.class)
    public ResponseEntity<CustomErrorResponse> recursoNaoEncontradoError(RecursoNaoEncontradoError erro, HttpServletRequest request) {
        CustomErrorResponse response = new CustomErrorResponse();
        response.setTimestamp(LocalDateTime.now()); // Data e hora do erro
        response.setStatus(HttpStatus.NOT_FOUND.value()); // Define como 404 o código de status
        response.setMessage(erro.getMessage()); //A mensagem de erro vinda do service
        response.setPath(request.getRequestURI()); // Qual endpoint ocorreu a requisição

        // Retorna o objeto com os dados e codigo 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ParametrosInsuficientesError.class)
    public ResponseEntity<CustomErrorResponse> parametrosInsuficientesError(ParametrosInsuficientesError erro , HttpServletRequest request) {
        CustomErrorResponse response = new CustomErrorResponse();

        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(erro.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomErrorResponse> badCredentialsException(BadCredentialsException erro, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse();

        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403 codigo
        response.setMessage("Email/senha invalidos");
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
