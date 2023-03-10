package com.gabriel.hksongguesser.api.exceptionhandler;

import com.gabriel.hksongguesser.domain.exception.EntidadeEmUsoException;
import com.gabriel.hksongguesser.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.hksongguesser.domain.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String MSG_ERRO_GENERICO = "Erro interno no sistema. Por favor tente novamente mais tarde.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUncaught(Exception ex, WebRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var type = ProblemaType.ERRO_GENERICO;
        var detail = MSG_ERRO_GENERICO;

        var problema = createProblemBuilder(status, type, detail)
                .userMessage(detail)
                .build();
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var type = ProblemaType.ERRO_GENERICO;
        var detail = ex.getMessage();

        var problema = createProblemBuilder(status, type, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEcontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var detail = ex.getMessage();
        var type = ProblemaType.ENTIDADE_NAO_ENCONTRADA;

        var problema = createProblemBuilder(status, type, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        var status = HttpStatus.CONFLICT;
        var detail = ex.getMessage();
        var type = ProblemaType.ENTIDADE_EM_USO;

        var problema = createProblemBuilder(status, type, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status, ProblemaType type, String detail) {
        return Problema.builder()
                .status(status.value())
                .type(type.getUri())
                .title(type.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = Problema.builder()
                    .status(status.value())
                    .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                    .userMessage(MSG_ERRO_GENERICO)
                    .timestamp(OffsetDateTime.now())
                    .build();
        }

        if (body instanceof String) {
            body = Problema.builder()
                    .status(status.value())
                    .title((String) body)
                    .userMessage(MSG_ERRO_GENERICO)
                    .timestamp(OffsetDateTime.now())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
