package com.vynicius.estagio.controller.common;

import com.vynicius.estagio.controller.dto.ErroCampo;
import com.vynicius.estagio.controller.dto.ErroResposta;
import com.vynicius.estagio.exceptions.ValorInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> erroCampos = fieldErrors.stream().map(fe ->
            new ErroCampo(
                    fe.getField(),
                    fe.getDefaultMessage())).collect(Collectors.toList());

        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "erro de validação", erroCampos);
    }



    @ExceptionHandler(ValorInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleValorInvalidoException(ValorInvalidoException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleIllegalArgumentException(IllegalArgumentException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleExeptionNaoTratada(RuntimeException e){
        return new ErroResposta(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocorreu um erro inesperado", List.of());
    }




}
