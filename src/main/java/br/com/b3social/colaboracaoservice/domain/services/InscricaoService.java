package br.com.b3social.colaboracaoservice.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.b3social.colaboracaoservice.api.consumers.AcaoSocialConsumer;
import br.com.b3social.colaboracaoservice.api.dtos.AcaoSocialDTO;
import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.models.enums.Status;
import br.com.b3social.colaboracaoservice.domain.repositories.ColaboracaoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class InscricaoService {
    @Autowired
    private ColaboracaoRepository repository;

    @Autowired
    AcaoSocialConsumer acaoSocialConsumer;

    public Colaboracao criarInscricao(Colaboracao inscricao, String usuarioId, String usuarioNome){
        String acaoSocialId = inscricao.getAcaoSocialId();
        AcaoSocialDTO acaoSocialDTO = acaoSocialConsumer.buscarAcaoSocialPorId(acaoSocialId);
        boolean naoCadastrado = validarCadastro(acaoSocialId, usuarioId);

        if(acaoSocialDTO != null && naoCadastrado){
            inscricao.setColaboradorId(usuarioId);
            inscricao.setColaboradorNome(usuarioNome);
            inscricao.setCoordenadorId(acaoSocialDTO.getCoordenadorId());
            inscricao.setStatus(Status.INSCRICAO_PENDENTE);
            if(acaoSocialDTO.getNivel() == 1) inscricao.setStatus(Status.INSCRICAO_DEFERIDA);
            return salvarInscricao(inscricao);
        }else if(!naoCadastrado){
            throw new EntityExistsException("Colaborador já inscrito");
        }

        throw new EntityNotFoundException("Ação social indisponível");
    }

    boolean validarCadastro(String acaoSocialId, String usuarioId){
        Optional<Colaboracao> colaboracao = this.repository.findByAcaoSocialIdAndColaboradorId(acaoSocialId, usuarioId);

        if(colaboracao.isPresent()) return false;

        return true;
    }

    public Integer buscarNumeroDeInscricaoPorId(String inscricaoId){
        Optional<Integer> inscricaoOptional = this.repository.countByAcaoSocialId(inscricaoId);

        if(inscricaoOptional.isPresent()){
            return inscricaoOptional.get();
        }

        return 0;
    }

    public Colaboracao buscarInscricaoPorId(String inscricaoId){
        Optional<Colaboracao> inscricaoOptional = this.repository.findById(inscricaoId);

        if(inscricaoOptional.isPresent()){
            return inscricaoOptional.get();
        }

        throw new EntityNotFoundException("Inscrição não encontrada");
    }

    public List<Colaboracao> buscarInscricaoPorAcaoSocial(String acaoSocialId, String coordenadorId){
        return this.repository.findByAcaoSocialIdAndCoordenadorId(acaoSocialId, coordenadorId);
    }

    public List<Colaboracao> buscarInscricaoPorColaborador(String colaboradorId){
        return this.repository.findByColaboradorId(colaboradorId);
    }

    public List<Colaboracao> buscarInscricaoPorCoordenador(String coordenadorId){
        return this.repository.findByCoordenadorId(coordenadorId);
    }

    public Colaboracao atualizarStatusInscricao(String inscricaoId, Status status){
        Optional<Colaboracao> inscricaoOptional = this.repository.findById(inscricaoId);

        if(inscricaoOptional.isPresent()){
            Colaboracao inscricao = inscricaoOptional.get();
            inscricao.setStatus(status);
            salvarInscricao(inscricao);

            return inscricao;
        }

        throw new EntityNotFoundException("Inscrição não encontrada");
    }

    public Colaboracao salvarInscricao(Colaboracao inscricao){
        return this.repository.save(inscricao);
    }
}
