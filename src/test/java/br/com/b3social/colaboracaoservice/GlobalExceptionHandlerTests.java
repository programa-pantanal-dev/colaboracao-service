package br.com.b3social.colaboracaoservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class GlobalExceptionHandlerTests {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    
    @Test
    void deveRetornarEntidadeNaoEncontradaQuandoSucesso(){
        EntityNotFoundException exception = mock(EntityNotFoundException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerEntityNotFoundException(exception);

        assertEquals("Entidade não encontrada", responseEntity.getBody().toString());
    }

    @Test
    void deveRetornarErroEmExecucaoQuandoSucesso(){
        RuntimeException exception = mock(RuntimeException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerRuntimeException(exception);

        assertEquals("Erro em execução", responseEntity.getBody().toString());
    }

    @Test
    void deveRetornarAcessoNegadoQuandoSucesso(){
        AccessDeniedException exception = mock(AccessDeniedException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerAccessDeniedException(exception);

        assertEquals("Acesso negado", responseEntity.getBody().toString());
    }

    @Test
    void deveRetornarASolicitacaoContemDadosInvalidosOuMalFormatadosQuandoSucesso(){
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerHttpMessageNotReadable(exception);

        assertEquals("A solicitação contém dados inválidos ou mal formatados", responseEntity.getBody().toString());
    }

    @Test
    void deveRetornarViolaoDeIntegridadeDeDadosQuandoSucesso(){
        DataIntegrityViolationException exception = mock(DataIntegrityViolationException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerDataErrors(exception);

        assertEquals("Violação de integridade de dados", responseEntity.getBody().toString());
    }

    @Test
    void deveRetornarUrlNaoSuportadaQuandoSucesso(){
        HttpRequestMethodNotSupportedException exception = mock(HttpRequestMethodNotSupportedException.class);

        ResponseEntity<?> responseEntity = globalExceptionHandler.handlerHttpRequestMethodNotSupportedException(exception);

        assertEquals("Endereço URL não suportado", responseEntity.getBody().toString());
    }
}
