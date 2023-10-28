package br.com.b3social.colaboracaoservice.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.b3social.colaboracaoservice.api.dtos.AtualizarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.api.dtos.CriarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.api.dtos.EmailDto;
import br.com.b3social.colaboracaoservice.api.dtos.ColaboracaoMapper;
import br.com.b3social.colaboracaoservice.api.dtos.RetornarColaboracaoDTO;
import br.com.b3social.colaboracaoservice.api.producers.EmailServiceProducer;
import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import br.com.b3social.colaboracaoservice.domain.services.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/colaboracao")
public class ColaboracaoController {
    @Autowired
    private InscricaoService inscricaoService;
    
    @Autowired
    EmailServiceProducer prod;

    @Autowired
    private ColaboracaoMapper mapper;

    @PostMapping
    @Operation(
        summary = "Realiza o cadastro de colaboração",
        method = "POST"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Sucesso ao cadastrar ação social"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Ação social não existe",
        content = @Content()
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<RetornarColaboracaoDTO> criarInscricao(@Valid @RequestBody CriarColaboracaoDTO criarInscricaoDTO, @AuthenticationPrincipal Jwt principal){
        Colaboracao inscricao = mapper.DtoToInscricao(criarInscricaoDTO);
        
        RetornarColaboracaoDTO retornarInscricaoDTO = mapper.InscricaoToDto(
            this.inscricaoService.criarInscricao(inscricao, principal.getSubject(), principal.getClaimAsString("name"), principal.getClaim("email"))
        );

        return new ResponseEntity<>(retornarInscricaoDTO, HttpStatus.CREATED);
    }

    @GetMapping("acaosocial/{id}")
    @Operation(
        summary = "Retorna todas as inscrições de uma ação social do coordenador logado",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao buscar colaborações"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<List<RetornarColaboracaoDTO>> buscarInscricoesPorAcaoSocial(@PathVariable String id, @AuthenticationPrincipal Jwt principal){
        List<Colaboracao> inscricoes = this.inscricaoService.buscarInscricaoPorAcaoSocial(id, principal.getSubject());

        List<RetornarColaboracaoDTO> inscrisoesDTO = inscricoes.stream().map(
            inscricao -> mapper.InscricaoToDto(inscricao)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(inscrisoesDTO, HttpStatus.OK);
    }

    @GetMapping("numero-colaboradores/{id}")
       @Operation(
        summary = "Retorna a quantidade de inscrições em uma ação social",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao buscar colaborações"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<Integer> buscarNumeroDeInscricoesPorAcaoSocial(@PathVariable String id){
        int inscricoes = this.inscricaoService.buscarNumeroDeInscricaoPorId(id);

        return new ResponseEntity<>(inscricoes, HttpStatus.OK);
    }

    @GetMapping("colaborador/{id}")
    @PreAuthorize("#id == authentication.name")
    @Operation(
        summary = "Retorna todas as inscrições de um colaborador",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao buscar colaborações"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<List<RetornarColaboracaoDTO>> buscarInscricoesPorColaborador(@PathVariable String id){
        List<Colaboracao> inscricoes = this.inscricaoService.buscarInscricaoPorColaborador(id);

        List<RetornarColaboracaoDTO> inscrisoesDTO = inscricoes.stream().map(
            inscricao -> mapper.InscricaoToDto(inscricao)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(inscrisoesDTO, HttpStatus.OK);
    }

    @GetMapping("coordenador/{id}")
    @PreAuthorize("#id == authentication.name")
    @Operation(
        summary = "Retorna todas as inscrições relacionadas as ações sociais de um coordenador",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao buscar colaborações"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<List<RetornarColaboracaoDTO>> buscarInscricoesPorCoordenador(@PathVariable String id){
        List<Colaboracao> inscricoes = this.inscricaoService.buscarInscricaoPorCoordenador(id);

        List<RetornarColaboracaoDTO> inscrisoesDTO = inscricoes.stream().map(
            inscricao -> mapper.InscricaoToDto(inscricao)).collect(Collectors.toList()
        );

        return new ResponseEntity<>(inscrisoesDTO, HttpStatus.OK);
    }
    
    @GetMapping("cancelar/{id}")
    @PreAuthorize("@resourceAccessService.isSubscriber(#id)")
    @Operation(
        summary = "Cancela colaboração pelo id",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao cancelar colaboração"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<RetornarColaboracaoDTO> cancelarInscricao(@PathVariable String id){
        Colaboracao inscricao = this.inscricaoService.atualizarStatusInscricao(id, Status.CANCELADA);

        RetornarColaboracaoDTO inscricaoDTO = mapper.InscricaoToDto(inscricao);

        return new ResponseEntity<>(inscricaoDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@resourceAccessService.isOwner(#id)")
    @Operation(
        summary = "Altera status da colaboração pelo id",
        method = "GET"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Sucesso ao atualizar colaboração"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Não autorizado",
        content = @Content()
    )
    public ResponseEntity<RetornarColaboracaoDTO> atualizarStatusInscricao(@PathVariable String id, @Valid @RequestBody AtualizarColaboracaoDTO atualizarInscricaoDTO){
        Colaboracao inscricao = this.inscricaoService.atualizarStatusInscricao(id, atualizarInscricaoDTO.getStatus());

        RetornarColaboracaoDTO inscricaoDTO = mapper.InscricaoToDto(inscricao);
        
        return new ResponseEntity<>(inscricaoDTO, HttpStatus.OK);
    }

    @GetMapping("/test")
    public void enviarEmail(){
        EmailDto email = new EmailDto();
        email.setEmailPara("fontes.ovelar@gmail.com");
        email.setAssunto("Test");
        email.setTexto("Teste de envio de email");
        this.prod.publishMessageEmailServiceAcaoSocial(email);
    }  
}
