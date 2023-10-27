package br.com.b3social.colaboracaoservice;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.services.InscricaoService;
import br.com.b3social.colaboracaoservice.security.expressions.ResourceAccessService;

@SpringBootTest
public class SecurityExpressionsTests {
    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @Mock
    InscricaoService mockService;

    @InjectMocks
    ResourceAccessService accessService;

    @Test
    void deveRetornarTrueQuandoCoordenadorAutorizado() throws Exception{
        Colaboracao inscricao = new Colaboracao();
        inscricao.setCoordenadorId("Teste");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(mockService.buscarInscricaoPorId(anyString())).thenReturn(inscricao);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Teste");

        assertTrue(this.accessService.isOwner("Teste"));
    }

    @Test
    void deveRetornarFalseQuandoCoordenadorNaoAutorizado() throws Exception{
        Colaboracao inscricao = new Colaboracao();
        inscricao.setCoordenadorId("Teste");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(mockService.buscarInscricaoPorId(anyString())).thenReturn(inscricao);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Teste");

        assertTrue(this.accessService.isOwner("Teste1"));
    }

    @Test
    void deveRetornarTrueQuandoColaboradorAutorizado() throws Exception{
        Colaboracao inscricao = new Colaboracao();
        inscricao.setColaboradorId("Teste");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(mockService.buscarInscricaoPorId(anyString())).thenReturn(inscricao);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Teste");

        assertTrue(this.accessService.isSubscriber("Teste"));
    }

    @Test
    void deveRetornarFalseQuandoColaboradorNaoAutorizado() throws Exception{
        Colaboracao inscricao = new Colaboracao();
        inscricao.setColaboradorId("Teste");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(mockService.buscarInscricaoPorId(anyString())).thenReturn(inscricao);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("Teste");

        assertTrue(this.accessService.isSubscriber("Teste1"));
    }
}
