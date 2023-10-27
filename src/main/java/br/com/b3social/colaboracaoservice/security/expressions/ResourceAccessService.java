package br.com.b3social.colaboracaoservice.security.expressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.b3social.colaboracaoservice.domain.models.Colaboracao;
import br.com.b3social.colaboracaoservice.domain.services.InscricaoService;

@Service("resourceAccessService")
public class ResourceAccessService {
    
    @Autowired
    private InscricaoService inscricaoService;
    Authentication authentication;

    public boolean isOwner(String resourceId) throws Exception{
        Colaboracao inscricao = this.inscricaoService.buscarInscricaoPorId(resourceId);
        String nome = SecurityContextHolder.getContext().getAuthentication().getName();

        if(nome.equals(inscricao.getCoordenadorId())) return true;
        
        return false;
    }

    public boolean isSubscriber(String resourceId) throws Exception{
        Colaboracao inscricao = this.inscricaoService.buscarInscricaoPorId(resourceId);
        String nome = SecurityContextHolder.getContext().getAuthentication().getName();

        if(nome.equals(inscricao.getColaboradorId())) return true;
        
        return false;
    }
}
