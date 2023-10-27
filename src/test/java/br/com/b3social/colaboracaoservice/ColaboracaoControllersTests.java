package br.com.b3social.colaboracaoservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.b3social.colaboracaoservice.api.dtos.AtualizarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.api.dtos.CriarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.api.dtos.ColaboracaoMapper;
import br.com.b3social.colaboracaoservice.api.dtos.RetornarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import br.com.b3social.colaboracaoservice.domain.services.InscricaoService;
import br.com.b3social.colaboracaoservice.security.expressions.ResourceAccessService;

@SpringBootTest
@AutoConfigureMockMvc
public class ColaboracaoControllersTests {
    @MockBean
    InscricaoService mockService;

    @MockBean
    ColaboracaoMapper mockMapper;

    @MockBean
    ResourceAccessService mockAccessService;

    @MockBean
    Authentication mockAuthentication;

    @MockBean
    AtualizarColaboracaoDTO atualizarInscricaoDTO;

    @MockBean(name = "resourceAccessService")
    public ResourceAccessService resourceAccessService;

    @Autowired
    private MockMvc mockMvc;

    public static final String BASE_URI = "/colaboracao";
    public static final String DF = "default";
    public static final String RANDOM_UIID = "dc7a2924-736b-11ee-b962-0242ac120002";

    @Test
    void retornaStatusCodeCreatedQuandoUsuarioAutorizado() throws Exception{
        CriarColaboracaoDTO inscricaoDTO = new CriarColaboracaoDTO();
        inscricaoDTO.setAcaoSocialId("Test");

        Colaboracao inscricao = new Colaboracao();
        RetornarColaboracaoDTO inscricaoDTO2 = new RetornarColaboracaoDTO();

        when(mockMapper.DtoToInscricao(any(CriarColaboracaoDTO.class))).thenReturn(inscricao);

        when(mockService.criarInscricao(any(Colaboracao.class), anyString(), anyString())).thenReturn(inscricao);

        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(inscricaoDTO2);

        this.mockMvc
            .perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(inscricaoDTO))
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                    .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DF))
                    .authorities(List.of(new SimpleGrantedAuthority(DF)))))
            .andExpect(status().isCreated());
    }

    @Test
    void retornaStatusCodeCreatedQuandoUsuarioNaoAutorizado() throws Exception{
        CriarColaboracaoDTO inscricaoDTO = new CriarColaboracaoDTO();
        inscricaoDTO.setAcaoSocialId("Test");

        Colaboracao inscricao = new Colaboracao();
        RetornarColaboracaoDTO inscricaoDTO2 = new RetornarColaboracaoDTO();

        when(mockMapper.DtoToInscricao(any(CriarColaboracaoDTO.class))).thenReturn(inscricao);

        when(mockService.criarInscricao(any(Colaboracao.class), anyString(), anyString())).thenReturn(inscricao);

        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(inscricaoDTO2);

        this.mockMvc
            .perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(inscricaoDTO)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarInscricoesPorAcaoSocialQuandoUsuarioAutorizado() throws Exception{
        when(mockService.buscarInscricaoPorAcaoSocial(anyString(), anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/acaosocial/{id}", RANDOM_UIID)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, DF))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoAutorizadoParaBuscarInscricaoPorUmaAcaoSocial() throws Exception{
        when(mockService.buscarInscricaoPorAcaoSocial(anyString(), anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/acaosocial/{id}", RANDOM_UIID))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarInscricoesPorColaboradorQuandoUsuarioAutorizado() throws Exception{
        when(mockAuthentication.getName()).thenReturn(RANDOM_UIID);
        when(mockService.buscarInscricaoPorColaborador(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/colaborador/{id}", RANDOM_UIID)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoAutorizadoParaBuscarInscricaoPorColaborador() throws Exception{
        when(mockAuthentication.getName()).thenReturn(RANDOM_UIID);
        when(mockService.buscarInscricaoPorColaborador(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/colaborador/{id}", DF)//Como o id não é o mesmo retornado no mock do authentication ele apresentada erro
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarInscricoesPorCoordenadorQuandoUsuarioAutorizado() throws Exception{
        when(mockAuthentication.getName()).thenReturn(RANDOM_UIID);
        when(mockService.buscarInscricaoPorCoordenador(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/coordenador/{id}", RANDOM_UIID)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    
    @Test
    void deveRetornarErroQuandoUsuarioNaoAutorizadoParaBuscarInscricaoPorCoordenador() throws Exception{
        when(mockAuthentication.getName()).thenReturn(RANDOM_UIID);
        when(mockService.buscarInscricaoPorColaborador(anyString())).thenReturn(List.of(new Colaboracao(), new Colaboracao()));
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/coordenador/{id}", DF)//Como o id não é o mesmo retornado no mock do authentication ele apresentada erro
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarInscricaoQuandoColaboradorTemPermissaoParaCancelar() throws Exception{
        when(resourceAccessService.isSubscriber(anyString())).thenReturn(true);
        when(mockService.atualizarStatusInscricao(RANDOM_UIID, Status.CANCELADA)).thenReturn(new Colaboracao());
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/cancelar/{id}", RANDOM_UIID)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveRetornarErroQuandoColaboradorNaoTemPermissaoParaCancelar() throws Exception{
        when(resourceAccessService.isSubscriber(anyString())).thenReturn(false);
        when(mockService.atualizarStatusInscricao(RANDOM_UIID, Status.CANCELADA)).thenReturn(new Colaboracao());
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(get(BASE_URI+"/cancelar/{id}", RANDOM_UIID)
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornarInscricaoQuandoCoordenadorTemPermissaoParaAtualizar() throws Exception{
        when(resourceAccessService.isOwner(anyString())).thenReturn(true);
        when(atualizarInscricaoDTO.getStatus()).thenReturn(any(Status.class));
        when(mockService.atualizarStatusInscricao(RANDOM_UIID, any(Status.class))).thenReturn(new Colaboracao());
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(put(BASE_URI+"/{id}", RANDOM_UIID)
                        .contentType("application/json")
                        .content(toJson(new AtualizarColaboracaoDTO()))
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isOk());
    }

    @Test
    void deveRetornarErroQuandoCoordenadorNaoTemPermissaoParaAtualizar() throws Exception{
        when(resourceAccessService.isOwner(anyString())).thenReturn(false);
        when(atualizarInscricaoDTO.getStatus()).thenReturn(any(Status.class));
        when(mockService.atualizarStatusInscricao(RANDOM_UIID, any(Status.class))).thenReturn(new Colaboracao());
        when(mockMapper.InscricaoToDto(any(Colaboracao.class))).thenReturn(new RetornarColaboracaoDTO());

        this.mockMvc
            .perform(put(BASE_URI+"/{id}", RANDOM_UIID)
                        .contentType("application/json")
                        .content(toJson(new AtualizarColaboracaoDTO()))
                .with(SecurityMockMvcRequestPostProcessors.jwt()
                .jwt(jwt -> jwt.claim(StandardClaimNames.SUB, RANDOM_UIID))))
            .andExpect(status().isUnauthorized());
    }

    private String toJson(final Object object) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (final Exception e) {
            throw new Exception("Error to convert object to json", e);
        }
    }
}
