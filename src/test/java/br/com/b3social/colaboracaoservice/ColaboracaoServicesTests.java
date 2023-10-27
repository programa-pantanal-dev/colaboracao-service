package br.com.b3social.colaboracaoservice;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.b3social.colaboracaoservice.api.consumers.AcaoSocialConsumer;
import br.com.b3social.colaboracaoservice.api.dtos.AcaoSocialDTO;
import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import br.com.b3social.colaboracaoservice.domain.repositories.ColaboracaoRepository;
import br.com.b3social.colaboracaoservice.domain.services.InscricaoService;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
public class ColaboracaoServicesTests {
    @Mock
    ColaboracaoRepository mockRepository;

    @Mock
    AcaoSocialConsumer mockConsumer;

    @InjectMocks
    InscricaoService mockService;
    
    public static final String DF = "default";

    @Test 
    void deveCriarUmaInscricaoComSucesso(){
        when(this.mockConsumer.buscarAcaoSocialPorId(any())).thenReturn(new AcaoSocialDTO());
        
        when(mockRepository.save(any(Colaboracao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Colaboracao.class));

        Colaboracao inscricaoCriada = mockService.criarInscricao(new Colaboracao(), DF, DF);

        assertNotNull(inscricaoCriada);
    }

    @Test 
    void deveCriarUmaInscricaoSemSucesso(){
        when(this.mockConsumer.buscarAcaoSocialPorId(anyString())).thenReturn(null);
        
        when(mockRepository.save(any(Colaboracao.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Colaboracao.class));

        assertThrows(EntityNotFoundException.class, () -> this.mockService.criarInscricao(new Colaboracao(), DF, DF));
    }

    @Test
    void deveBuscarInscricaoPorIdComSucesso(){
        Optional<Colaboracao> inscricaoOptional = Optional.of(new Colaboracao());

        when(mockRepository.findById(anyString())).thenReturn(inscricaoOptional);

        Colaboracao inscricao = this.mockService.buscarInscricaoPorId(anyString());

        assertNotNull(inscricao);
    }

    @Test
    void deveBuscarInscricaoPorIdSemSucesso(){
        when(mockRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> this.mockService.buscarInscricaoPorId(anyString()));
    }

    @Test
    void deveBuscarInscricaoPorAcaoSocialComSucesso(){
        when(mockRepository.findByAcaoSocialIdAndCoordenadorId(anyString(), anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));

        List<Colaboracao> inscricoes = this.mockService.buscarInscricaoPorAcaoSocial(anyString(), anyString());

        assertFalse(inscricoes.isEmpty());
    }

    @Test
    void deveBuscarInscricaoPorColaboradorComSucesso(){
        when(mockRepository.findByColaboradorId(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));

        List<Colaboracao> inscricoes = this.mockService.buscarInscricaoPorColaborador(anyString());

        assertFalse(inscricoes.isEmpty());
    }

    @Test
    void deveBuscarInscricaoPorCoordenadorComSucesso(){
        when(mockRepository.findByCoordenadorId(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));

        List<Colaboracao> inscricoes = this.mockService.buscarInscricaoPorCoordenador(anyString());

        assertFalse(inscricoes.isEmpty());
    }

    @Test
    void deveAtualizarStatusDeInscricaoComSucesso(){
        Optional<Colaboracao> inscricaoOptional = Optional.of(new Colaboracao());

        when(mockRepository.findById(anyString())).thenReturn(inscricaoOptional);

        Colaboracao inscricao = mockService.atualizarStatusInscricao(anyString(), Status.INSCRICAO_DEFERIDA);

        assertNotNull(inscricao);
    }

    @Test
    void deveAtualizarStatusDeInscricaoSemSucesso(){
        when(mockRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> this.mockService.atualizarStatusInscricao(anyString(), Status.INSCRICAO_DEFERIDA));
    }
}
